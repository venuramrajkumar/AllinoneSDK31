package com.raj.mycarride.di.modules

import com.example.allinone.ui.login.api.AuthApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class NetworkModule {

    @Provides
    fun provideImageDownloadAPI ( retrofit: Retrofit) : AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

}