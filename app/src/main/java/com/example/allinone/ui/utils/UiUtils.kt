package com.example.allinone.ui.utils

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.allinone.ui.login.home.HomeActivity
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
    requireView().snackbar(failure)
}





