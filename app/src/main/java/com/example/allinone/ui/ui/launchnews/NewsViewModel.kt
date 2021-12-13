package com.example.allinone.ui.ui.launchnews

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import android.util.Log
import androidx.lifecycle.*
import com.example.allinone.ui.NewsApplication
import com.example.allinone.ui.model.Article
import com.example.allinone.ui.model.NewsResponse
import com.example.allinone.ui.repository.NewsRepository
import com.example.allinone.ui.utils.NetworkUtils
import com.example.allinone.ui.utils.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class NewsViewModel(application: Application, val newsRepository: NewsRepository) :
    AndroidViewModel(application) {
    //Extending AndroidViewModel to access the application context to check internet active
    private val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    private val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val breakingNewsLiveData: LiveData<Resource<NewsResponse>> = breakingNews
    val searchNewsLiveData: LiveData<Resource<NewsResponse>> = searchNews
    var breakingNewsPage = 1
    var searchNewsPage = 1
    var breakingNewsResponse: NewsResponse? = null
    var searchNewsResponse: NewsResponse? = null

    var category: String = "business"

    val breakingNewsExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        breakingNews.postValue(Resource.Error(throwable.message!!))
        Log.e("NewsViewModel", "ERROR OCCURED")
    }

    val searchNewsExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        searchNews.postValue(Resource.Error(throwable.message!!))
        Log.e("NewsViewModel", "ERROR OCCURED in SearchNews Call")
    }

    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch(breakingNewsExceptionHandler) {
        breakingNews.value = Resource.Loading()
        try {
            if (hasActiveInternet()) {
                val response = newsRepository.getBreakingNews(countryCode, category, breakingNewsPage)
                breakingNews.postValue(handleBreakingNewsResponse(response))
            } else {
                breakingNews.postValue(Resource.Error("NO INTERNENT"))
            }
        } catch (e : Throwable) {
            when (e) {
                is IOException ->   breakingNews.postValue(Resource.Error("NO INTERNENT"))
                else -> breakingNews.postValue(Resource.Error("SOME THING ELSE ERROR"))
            }
        }


//        Regular way of exception handling with Try catch. But we can use coroutine Exception Handler
//        try {
//            val response = newsRepository.getBreakingNews(countryCode, category)
//            breakingNews.postValue(handleNewsResponse(response))
//        } catch (e: Exception) {
//            breakingNews.postValue( Resource.Error(e.message!!))
//        }

    }

    fun searchNews(query: String) =
        viewModelScope.launch(searchNewsExceptionHandler) {
            searchNews.postValue(Resource.Loading())
            try {
                if (NetworkUtils.hasActiveInternet()) {
                    val response = newsRepository.getSearchedNews(query, searchNewsPage)
                    searchNews.postValue(handleSearchNewsResponse(response))
                } else {
                    searchNews.postValue(Resource.Error("NO INTERNENT"))
                }
            } catch (e: Throwable) {
                when (e) {
                    is IOException ->   searchNews.postValue(Resource.Error("NO INTERNENT"))
                    else -> searchNews.postValue(Resource.Error("SOME THING ELSE ERROR"))
                }
            }



        }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {

        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                breakingNewsPage++

                if (breakingNewsResponse == null) {
                    breakingNewsResponse = resultResponse
                } else {
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)

                }

                return Resource.Success(breakingNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {

        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                searchNewsPage++

                if (searchNewsResponse == null) {
                    searchNewsResponse = resultResponse
                } else {
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)

                }

                return Resource.Success(searchNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveAndUpdateArticles(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.saveAndUpdateArticles(article)
        }
    }

    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

    fun hasActiveInternet(): Boolean {
        val connectivityManager: ConnectivityManager =
            getApplication<NewsApplication>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return  when(type) {
                    TYPE_WIFI ->  true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }


    override fun onCleared() {
        super.onCleared()
        //When ever viewModel is observing some data, clear the observer here to avoid memory leak.
        //This will be called before android process get killed by OS / App kept in background for long time

        Log.d("NewsViewModel", " onCleared  ")
    }


}