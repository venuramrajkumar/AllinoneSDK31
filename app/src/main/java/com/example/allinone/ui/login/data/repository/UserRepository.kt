package com.example.allinone.ui.login.data.repository


import com.example.allinone.ui.login.api.TokenRefreshApi
import com.example.allinone.ui.login.api.UserApi
import com.example.allinone.ui.login.data.UserPreferences
import com.example.allinone.ui.login.data.model.LoginResponse
import com.example.allinone.ui.login.data.model.TokenResponse
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserRepository @Inject constructor(
    var userAPi: UserApi,
    var tokenRefreshApi: TokenRefreshApi,
    var userPreferences: UserPreferences
) {

    fun login(map : Map<String,String>): Single<LoginResponse> {
        return userAPi.getUser(map)
    }

    suspend fun getSavedRefreshToken() : String {
        return userPreferences.refreshToken.first().toString()
    }

    suspend fun saveTokens(access: String, refreshToken: String) {
        userPreferences.saveAccessTokens(access,refreshToken)
    }

    fun getRefreshToken(refreshToken : String) : Single<TokenResponse> {
        return tokenRefreshApi.refreshAccessToken(refreshToken)
    }




    suspend fun logout() = userAPi.logout()


}

