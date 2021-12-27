package com.example.allinone.ui.utils

import okhttp3.ResponseBody

sealed class Resource<out T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<out T>(data: T) : Resource<T>(data)
    class Error<out T>(message: String, data: T? = null) : Resource<T>(data, message)
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ) : Resource<Nothing>()
    class Loading<out T> : Resource<T>()
}

//sealed class Resource<out T> {
//    data class Success<out T>(val value: T) : Resource<T>()
//    data class Failure(
//        val isNetworkError: Boolean,
//        val errorCode: Int?,
//        val errorBody: ResponseBody?
//    ) : Resource<Nothing>()
//    object Loading : Resource<Nothing>()
//}