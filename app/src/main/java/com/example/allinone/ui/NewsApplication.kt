package com.example.allinone.ui

import com.example.allinone.R
import com.example.allinone.ui.di.component.DaggerAppComponent
import com.example.allinone.ui.notification.MyAppsNotificationManager
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class NewsApplication : DaggerApplication() {

    companion object {
        lateinit var application: NewsApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        application = this

        //DaggerAppComponent.create() : Normal with passing context with @BindInstance

        MyAppsNotificationManager.registerNotificationChannelChannel(
            getString(R.string.NEWS_CHANNEL_ID),
            getString(R.string.CHANNEL_NEWS),
            getString(R.string.CHANNEL_DESCRIPTION),
            this.applicationContext
        )

    }

    override fun applicationInjector(): AndroidInjector<out NewsApplication> {
        return DaggerAppComponent.factory().create(this)
    }
}
