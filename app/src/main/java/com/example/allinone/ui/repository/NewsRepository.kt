package com.example.allinone.ui.repository

import com.example.allinone.ui.db.ArticleDatabase
import com.example.allinone.ui.rest.api.RetrofitInstance

class NewsRepository(val databse: ArticleDatabase) {

    suspend fun getBreakingNews(countryCode: String, category: String) =
        RetrofitInstance.api.getBreakingNews(countryCode, category)

}