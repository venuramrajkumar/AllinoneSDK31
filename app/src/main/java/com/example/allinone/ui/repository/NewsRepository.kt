package com.example.allinone.ui.repository

import com.example.allinone.ui.db.ArticleDatabase
import com.example.allinone.ui.model.Article
import com.example.allinone.ui.rest.api.RetrofitInstance

class NewsRepository(val databse: ArticleDatabase) {

    suspend fun getBreakingNews(countryCode: String, category: String,pageNumber : Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber, category)

    suspend fun getSearchedNews(searchQuery: String,pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery,pageNumber)

    suspend fun saveAndUpdateArticles(article: Article) =
        databse.getArticleDao().saveAndUpdateArticles(article)

     fun getSavedNews() = databse.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = databse.getArticleDao().deleteArticle(article)

}