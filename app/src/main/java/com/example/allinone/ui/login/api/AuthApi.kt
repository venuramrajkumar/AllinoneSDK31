package com.example.allinone.ui.login.api


import com.example.allinone.ui.login.model.LoginResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {


    @FormUrlEncoded
    @POST("auth/login")
    fun login(
        @Field("email") email : String,
        @Field("password") password : String,
    ) : Single<LoginResponse>

    /*@POST("/public/v1/users")
    fun login(
        @HeaderMap headerMap: Map<String,String>,
        @Query("name") name :String,
        @Query("email") email : String,
        @Query("gender") gender : String,
        @Query("status") status : String = "active"
    ) : Single<LoginResponse>*/



}