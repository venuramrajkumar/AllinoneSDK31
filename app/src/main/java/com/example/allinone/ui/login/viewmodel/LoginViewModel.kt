package com.example.allinone.ui.login.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.allinone.ui.login.model.LoginResponse

import com.example.allinone.ui.login.repository.AuthRepository
import com.example.allinone.ui.utils.Resource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class LoginViewModel @Inject constructor(var authRepository: AuthRepository) : ViewModel() {


    private var disposable : CompositeDisposable = CompositeDisposable()
    private var _loginResponseLiveData = MutableLiveData<Resource<LoginResponse>>()
    val loginResponseLiveData : LiveData<Resource<LoginResponse>> get() = _loginResponseLiveData

    fun makeLogin(email : String, password : String) {
        authRepository.login(email,password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(getSingleObserver())
    }

    private fun getSingleObserver(): SingleObserver<LoginResponse> {
        return object : SingleObserver<LoginResponse> {
            override fun onSuccess(t: LoginResponse) {
                Log.d("AuthRepository", t.user.toString())
                _loginResponseLiveData.postValue(Resource.Success(t))
            }

            override fun onError(e: Throwable) {
                Log.d("AuthRepository", e.message.toString())
                _loginResponseLiveData.postValue(Resource.Error(e.message.toString()))
            }

            override fun onSubscribe(d: io.reactivex.rxjava3.disposables.Disposable) {
                disposable.add(d)
            }

        }
    }

    fun dispose() {
        disposable.dispose()
    }

    override fun onCleared() {
        super.onCleared()
        dispose()
    }


}