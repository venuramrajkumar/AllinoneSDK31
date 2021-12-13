package com.example.allinone.ui

import android.app.Application
import com.example.allinone.ui.utils.NetworkUtils

class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        NetworkUtils.application = this
    }
}
