package com.example.allinone.ui.utils

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.allinone.ui.login.home.HomeActivity
import com.example.allinone.ui.login.ui.LoginFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

fun<A : Activity> Activity.startNewActivity(activity : Class<A>) {
    Intent(this,activity).apply {
        this.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(this)
    }
}

fun View.visible(isVisible : Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enabled : Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()
}

fun Fragment.logout() = lifecycleScope.launch {
    if (activity is HomeActivity) {
        (activity as HomeActivity).performLogout()
    }
}

fun Fragment.handleApiError(
    failure: String,
    retry: (() -> Unit)? = null
) {
    requireView().snackbar(failure,retry)
}

fun Fragment.handleApiErrors(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
    when {
        failure.isNetworkError -> requireView().snackbar(
            "Please check your internet connection",
            retry
        )
        failure.errorCode == 401 -> {
            if (this is LoginFragment) {
                requireView().snackbar("You've entered incorrect email or password")
            } else {
                logout()
            }
        }
        else -> {
            val error = failure.errorBody?.string().toString()
            requireView().snackbar(error)
        }
    }

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

//fun Fragment.handleApiError(
//    failure: Resource.Failure,
//    retry: (() -> Unit)? = null
//) {
//    when {
//        failure.isNetworkError -> requireView().snackbar(
//            "Please check your internet connection",
//            retry
//        )
//        failure.errorCode == 401 -> {
//            if (this is LoginFragment) {
//                requireView().snackbar("You've entered incorrect email or password")
//            } else {
//                logout()
//            }
//        }
//        else -> {
//            val error = failure.errorBody?.string().toString()
//            requireView().snackbar(error)
//        }
//    }
//}



