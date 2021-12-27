package com.example.allinone.ui.login.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allinone.ui.login.data.model.LoginResponse
import com.example.allinone.ui.login.data.repository.UserRepository
import com.example.allinone.ui.utils.Resource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: UserRepository,

    ) : ViewModel() {

    private val _user: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val user: LiveData<Resource<LoginResponse>>
        get() = _user

    val compositeDisposable: CompositeDisposable = CompositeDisposable()
    lateinit var savedRefreshToken: String



    fun getUser() {

        runBlocking {
            viewModelScope.launch {
                savedRefreshToken = repository.getSavedRefreshToken()
            }
        }
        _user.postValue(Resource.Loading())
        repository.getRefreshToken(savedRefreshToken)
            .subscribeOn(Schedulers.io())
            .flatMap { tokenResponse ->
                viewModelScope.launch {
                    repository.saveTokens(tokenResponse.access_token!!,tokenResponse.refresh_token!!)
                }
                repository.login(getHeaderMap(tokenResponse.access_token))
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { Log.d("HomeViewModel", "Error") }
            .subscribe(object : SingleObserver<LoginResponse> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(t: LoginResponse) {
                    _user.postValue(Resource.Success(t))

                }

                override fun onError(e: Throwable) {
                    Log.d("HomeViewModel error ", e.message.toString())
                    var error : Resource.Failure? = null

                    when (e) {
                        is HttpException -> {
                            error = Resource.Failure(false, e.code(), e.response()?.errorBody())
                        }
                        else -> {
                            error = Resource.Failure(true, null, null)
                        }
                    }

                    _user.postValue(error!!)
                }

            })

    }

    suspend fun logout() = withContext(Dispatchers.IO) {
        val accessToken = repository.getSavedAccessToken()
        repository.logout(getHeaderMap(accessToken)) }

    fun getHeaderMap(refreshToken: String?): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = "Bearer " + refreshToken
        headerMap["Content-Type"] = "application/json"
        return headerMap
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}