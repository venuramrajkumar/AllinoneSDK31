package com.example.allinone.ui.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.allinone.ui.NewsApplication

object NetworkUtils {

    fun hasActiveInternet(): Boolean {
        val connectivityManager: ConnectivityManager =
            NewsApplication.application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
//            return connectivityManager.activeNetworkInfo?.isConnected
            connectivityManager.activeNetworkInfo?.run {
                return  when(type) {
                    ConnectivityManager.TYPE_WIFI ->  true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }

            }
        }
        return false
    }

    fun getToken() : String = "e48182773fa67711b131bbd4457d633aa1b5d85481b476155fa63c8517e6b276"

}