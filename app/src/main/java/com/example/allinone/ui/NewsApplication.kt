package com.example.allinone.ui

import com.example.allinone.ui.di.component.DaggerAppComponent
import com.example.allinone.ui.utils.NetworkUtils
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class NewsApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        NetworkUtils.application = this

        //DaggerAppComponent.create() : Normal with passing context with @BindInstance

    }

    override fun applicationInjector(): AndroidInjector<out NewsApplication> {
        return  DaggerAppComponent.factory().create(this)
    }
}
