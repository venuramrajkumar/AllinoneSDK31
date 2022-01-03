package com.example.allinone.ui

import android.util.Log
import android.widget.Toast
import com.example.allinone.R
import com.example.allinone.ui.di.component.DaggerAppComponent
import com.example.allinone.ui.notification.MyAppsNotificationManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
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

        //Firebase Messaging
        FirebaseMessaging.getInstance().isAutoInitEnabled = true

//        FirebaseMessaging.getInstance().token.addOnCompleteListener {
//                task -> if (!task.isSuccessful) {
//            Log.d(resources.getString(R.string.Debug_Tag), "Task Failed")
//            return@addOnCompleteListener
//        }
//            val token = task.result
//            Toast.makeText(baseContext,"Token " + token,Toast.LENGTH_SHORT).show()
////            Log.d(resources.getString(R.string.Debug_Tag), "Token 40 is ${FirebaseMessaging.getInstance().token.result}")
//        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("NewsApplication", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = getString(R.string.msg_token_fmt, token)
            Log.d("NewsApplication", msg)
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })



    }

    override fun applicationInjector(): AndroidInjector<out NewsApplication> {
        return DaggerAppComponent.factory().create(this)
    }
}
