package com.example.allinone.ui.rest.api

import com.example.allinone.ui.model.NewsResponse
import com.example.allinone.ui.utils.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query ("country")
        countryCode : String = "us",

        @Query("page")
        pagenumber: Int ,

        @Query("category")
        category: String = "business",

        @Query("apiKey")
        apikey : String = API_KEY
    ) : Response<NewsResponse>


    @GET("v2/everything")
    suspend fun searchForNews(
        @Query ("q")
        searchquery : String,

        /*@Query("from")
        fromDate: String,

        @Query("to")
        toDate : String,

        @Query("sortBy")
        sortBy : String = "popularity",*/
        @Query("page")
        pagenumber: Int ,

        @Query("apiKey")
        apikey : String = API_KEY

    ) : Response<NewsResponse>
}