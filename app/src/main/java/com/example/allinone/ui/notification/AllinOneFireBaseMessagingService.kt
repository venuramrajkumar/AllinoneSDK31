package com.example.allinone.ui.notification

import android.app.PendingIntent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.allinone.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

// Register this service in androidManifest.
class AllinOneFireBaseMessagingService :  FirebaseMessagingService() {

    internal enum class PUSH_NOTIFICATION_SOURCE {
        CONSOLE, API_WITHOUT_NOTIFICATION, API_WITH_NOTIFICATION, UNKNOWN_SOURCE
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(getString(R.string.Debug_Tag), "Message is " + remoteMessage.toString())

        val notificationSource: PUSH_NOTIFICATION_SOURCE = getNotificationSource(remoteMessage)

        Log.i(
            getString(R.string.Debug_Tag),
            "Remote Message received from : $notificationSource"
        )

        when (notificationSource) {

            PUSH_NOTIFICATION_SOURCE.CONSOLE -> MyAppsNotificationManager.triggerNotificationWithBackStack(
                NotificationDetailActivity::class.java,
                getString(R.string.NEWS_CHANNEL_ID),
                remoteMessage.notification!!.title!!,
                remoteMessage.notification!!.body!!,
                "This notification is from FCM Console ",
                NotificationCompat.PRIORITY_HIGH,
                false,
                resources.getInteger(R.integer.notificationId),
                PendingIntent.FLAG_UPDATE_CURRENT,
                context = this.applicationContext
            )

            PUSH_NOTIFICATION_SOURCE.API_WITH_NOTIFICATION -> MyAppsNotificationManager.triggerNotificationWithBackStack(
                NotificationDetailActivity::class.java,
                getString(R.string.NEWS_CHANNEL_ID),
                remoteMessage.notification!!.title!!,
                remoteMessage.notification!!.body!!,
                "This notification is from FCM API call with notification title and body",
                NotificationCompat.PRIORITY_HIGH,
                false,
                resources.getInteger(R.integer.notificationId),
                PendingIntent.FLAG_UPDATE_CURRENT,
                this.applicationContext
            )
            PUSH_NOTIFICATION_SOURCE.API_WITHOUT_NOTIFICATION -> MyAppsNotificationManager.triggerNotificationWithBackStack(
                NotificationDetailActivity::class.java,
                getString(R.string.NEWS_CHANNEL_ID),
                "Random notification Title",
                "Random notification body",
                "This notification is from FCM API call without notification title and body",
                NotificationCompat.PRIORITY_HIGH,
                false,
                resources.getInteger(R.integer.notificationId),
                PendingIntent.FLAG_UPDATE_CURRENT,
                this.applicationContext
            )
            PUSH_NOTIFICATION_SOURCE.UNKNOWN_SOURCE -> Log.i(
                "NOtification Unknown",
                "Since it's unknown source, don't want to do anything"
            )
            else -> {}
        }


        // WHen application is in foreground this is our responsibility to trigger notification.
//        MyAppsNotificationManager.triggerNotification(
//            NotificationDetailActivity::class.java,
//            getString(R.string.NEWS_CHANNEL_ID),
//            remoteMessage.notification?.title!!,
//            remoteMessage.notification?.body!!,
//            "BigNews",
//            1,
//            false,
//            resources.getInteger(R.integer.notificationId),
//            PendingIntent.FLAG_UPDATE_CURRENT,
//            this.applicationContext
//        )
    }

    private fun getNotificationSource(remoteMessage: RemoteMessage): PUSH_NOTIFICATION_SOURCE {
        val notificationSource: PUSH_NOTIFICATION_SOURCE
        val notification = remoteMessage.notification
        val data = remoteMessage.data
        notificationSource = if (notification != null && data != null) {
            if (data.size == 0) {
                PUSH_NOTIFICATION_SOURCE.CONSOLE
            } else {
                PUSH_NOTIFICATION_SOURCE.API_WITH_NOTIFICATION
            }
        } else if (remoteMessage.data != null) {
            PUSH_NOTIFICATION_SOURCE.API_WITHOUT_NOTIFICATION
        } else {
            PUSH_NOTIFICATION_SOURCE.UNKNOWN_SOURCE
        }
        return notificationSource
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()
    }

    override fun onMessageSent(p0: String) {
        super.onMessageSent(p0)
    }

    override fun onSendError(p0: String, p1: Exception) {
        super.onSendError(p0, p1)
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.d(resources.getString(R.string.Debug_Tag), "Token is " + p0)


    }
}