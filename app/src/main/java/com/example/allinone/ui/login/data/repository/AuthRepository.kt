package com.example.allinone.ui.login.data.repository


import com.example.allinone.ui.login.api.AuthApi
import com.example.allinone.ui.login.data.UserPreferences
import com.example.allinone.ui.login.data.model.LoginResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class AuthRepository @Inject constructor(
    var iAuthApi: AuthApi,
    var userPreferences: UserPreferences
) {

    fun login(email: String, password: String): Single<LoginResponse> {
        return iAuthApi.login(email, password)
    }

     suspend fun saveAuthToken(authToken: String,refreshToken : String) {
            userPreferences.saveAccessTokens(authToken,refreshToken)

    }


//    private fun getHeaderMap(): Map<String, String> {
//        val headerMap = mutableMapOf<String, String>()
//        headerMap["Authorization"] = "Bearer " + NetworkUtils.getToken()
//        headerMap["Content-Type"] = "application/json"
//        return headerMap
//    }


}

