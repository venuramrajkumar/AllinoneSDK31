package com.example.allinone.ui.ui.launchnews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allinone.ui.model.NewsResponse
import com.example.allinone.ui.repository.NewsRepository
import com.example.allinone.ui.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(val newsRepository: NewsRepository) : ViewModel() {

    private val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val breakingNewsLiveData : LiveData<Resource<NewsResponse>> = breakingNews
    var breakingNewsPage = 1
    var category: String = "business"

    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {

        breakingNews.value = Resource.Loading()
        val response = newsRepository.getBreakingNews(countryCode, category)
        breakingNews.postValue(handleNewsResponse(response))
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

    }


}