package com.example.allinone.ui.login.api


import com.example.allinone.ui.login.data.model.LoginResponse
import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface UserApi {

    @GET("user")
    fun getUser(
        @HeaderMap map : Map<String,String>
    ): Single<LoginResponse>

    @POST("logout")
    suspend fun logout(): ResponseBody

}