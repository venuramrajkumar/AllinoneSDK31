package com.example.allinone.ui.ui.launchnews

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.allinone.ui.db.ArticleDatabase
import com.example.allinone.ui.repository.NewsRepository

class ViewModelProviderFactory(val applicationContext: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(NewsRepository(ArticleDatabase(applicationContext))) as T
    }
}