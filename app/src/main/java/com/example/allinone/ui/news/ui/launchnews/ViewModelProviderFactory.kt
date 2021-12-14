package com.example.allinone.ui.news.ui.launchnews

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.allinone.ui.news.db.ArticleDatabase
import com.example.allinone.ui.news.repository.NewsRepository

class ViewModelProviderFactory(val app: Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(app ,NewsRepository(ArticleDatabase(app))) as T
    }
}