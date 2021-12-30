package com.example.allinone.ui.notification

import android.app.PendingIntent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.allinone.R
import com.example.allinone.databinding.ActivityNotificationBinding

class NotificationDemoActivity : AppCompatActivity() {

    lateinit var binding: ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Registering channel is done in Application class.

        binding.triggernotification.setOnClickListener {

            MyAppsNotificationManager.triggerNotification(
                NotificationDetailActivity::class.java,
                getString(R.string.NEWS_CHANNEL_ID),
                "News",
                "Breaking News",
                "BigNews",
                1,
                false,
                resources.getInteger(R.integer.notificationId),
                PendingIntent.FLAG_UPDATE_CURRENT,
                this.applicationContext
            )

        }

        binding.cancelNotification.setOnClickListener {
            MyAppsNotificationManager.cancelNotification(
                resources.getInteger(R.integer.notificationId),
                NotificationManagerCompat.from(this.applicationContext)
            )
        }

        binding.updateNotification.setOnClickListener {
            MyAppsNotificationManager.updateWithPicture(
                NotificationDetailActivity::class.java,
                "Updated News",
                "Breaking Burst News",
                getString(R.string.NEWS_CHANNEL_ID),
                resources.getInteger(R.integer.notificationId),
                "This is a updated information for bigpicture String",
                PendingIntent.FLAG_UPDATE_CURRENT,
                this.applicationContext
            )
        }

        binding.stackBuilderNavigation.setOnClickListener {

            MyAppsNotificationManager.triggerNotificationWithBackStack(
                NotificationDetailActivity::class.java,
                getString(R.string.NEWS_CHANNEL_ID),
                "Gmail N0tification",
                "Gmail Detail -> Gmail list-> Home screen",
                "GMAIL",
                NotificationCompat.PRIORITY_DEFAULT,
                true,
                PendingIntent.FLAG_UPDATE_CURRENT,
                resources.getInteger(R.integer.notificationId),
                this.applicationContext
            )
        }


    }
}