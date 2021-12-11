package com.example.allinone.ui.ui.launchnews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.allinone.ui.repository.NewsRepository

class ViewModelProviderFactory(val newsRepository: NewsRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository) as T
    }
}