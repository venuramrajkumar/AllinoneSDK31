package com.example.allinone.ui.ui.launchnews

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allinone.ui.model.NewsResponse
import com.example.allinone.ui.repository.NewsRepository
import com.example.allinone.ui.utils.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(val newsRepository: NewsRepository) : ViewModel() {

    private val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val breakingNewsLiveData: LiveData<Resource<NewsResponse>> = breakingNews
    var breakingNewsPage = 1
    var category: String = "business"

    val exceptionHander = CoroutineExceptionHandler { coroutineContext, throwable ->
        breakingNews.postValue(Resource.Error(throwable.message!!))
        Log.e("NewsViewModel","ERROR OCCURED")
    }

    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch(exceptionHander) {

        breakingNews.value = Resource.Loading()
        val response = newsRepository.getBreakingNews(countryCode, category)
        breakingNews.postValue(handleNewsResponse(response))

//        Regular way of exception handling with Try catch. But we can use coroutine Exception Handler
//        try {
//            val response = newsRepository.getBreakingNews(countryCode, category)
//            breakingNews.postValue(handleNewsResponse(response))
//        } catch (e: Exception) {
//            breakingNews.postValue( Resource.Error(e.message!!))
//        }

    }

    private fun handleNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {

        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    override fun onCleared() {
        super.onCleared()
        //When ever viewModel is observing some data, clear the observer here to avoid memory leak.
        //This will be called before android process get killed by OS / App kept in background for long time
    }


}