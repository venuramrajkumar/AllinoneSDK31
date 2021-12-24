package com.example.allinone.ui.login.api

import com.example.allinone.ui.login.data.model.TokenResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface TokenRefreshApi  {
    @FormUrlEncoded
    @POST("auth/refresh-token")
    @Headers("Accept: application/json")
    fun refreshAccessToken(
        @Field("refresh_token") refreshToken: String?
    ): Single<TokenResponse>
}