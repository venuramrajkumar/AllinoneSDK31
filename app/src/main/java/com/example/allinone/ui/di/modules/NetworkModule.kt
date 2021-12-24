package com.raj.mycarride.di.modules

import com.example.allinone.ui.login.api.AuthApi
import com.example.allinone.ui.login.api.TokenRefreshApi
import com.example.allinone.ui.login.api.UserApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideAuthAPI ( retrofit: Retrofit) : AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Singleton
    @Provides
    fun provideUserAPI ( retrofit: Retrofit) : UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Singleton
    @Provides
    fun provideTokenApi ( retrofit: Retrofit) : TokenRefreshApi {
        return retrofit.create(TokenRefreshApi::class.java)
    }

}