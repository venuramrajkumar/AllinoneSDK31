package com.example.allinone.ui.notification


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import com.example.allinone.R

object MyAppsNotificationManager {

    fun registerNotificationChannelChannel(
        channelId: String,
        channelName: String,
        channelDescription: String,
        context: Context
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.description = channelDescription
            val notificationManager = context.getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    fun triggerNotification(
        targetNotificationActivity: Class<*>?,
        channelId: String,
        title: String,
        text: String,
        bigText: String,
        priority: Int = NotificationCompat.PRIORITY_DEFAULT,
        autoCancel: Boolean = true,
        notificationId: Int,
        context: Context
    ) {
        val intent = Intent(context, targetNotificationActivity)
        intent.putExtra("count", title)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(
            context,
            channelId
        ).apply {
            setSmallIcon(R.drawable.ic_notification)
            setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_icon_large))
            setContentTitle(title)
            setContentText(text)
            setStyle(NotificationCompat.BigTextStyle().bigText(bigText))
            setPriority(priority)
            setContentIntent(pendingIntent)
            setChannelId(channelId)
            setAutoCancel(autoCancel)
        }

        val notificationManagerCompat: NotificationManagerCompat = NotificationManagerCompat.from(
            context
        )
        notificationManagerCompat.notify(notificationId, builder.build())
    }

    fun triggerNotification(
        targetNotificationActivity: Class<*>?,
        channelId: String,
        title: String,
        text: String,
        bigText: String,
        priority: Int,
        autoCancel: Boolean,
        notificationId: Int,
        pendingIntentFlag: Int,
        context: Context
    ) {
        val intent = Intent(context, targetNotificationActivity)
        intent.putExtra("count", title)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, pendingIntentFlag)
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(context, channelId!!)
            .setSmallIcon(R.drawable.ic_notification)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_icon_large))
            .setContentTitle(title)
            .setContentText(text)
            .setStyle(NotificationCompat.BigTextStyle().bigText(bigText))
            .setPriority(priority)
            .setContentIntent(pendingIntent)
            .setChannelId(channelId)
            .setAutoCancel(autoCancel)
//            .setOngoing(true)
        //If setOnGoing made true, notification will not go away until we cancel is explicitly.

        val notificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManagerCompat.notify(notificationId, builder.build())
    }


    fun triggerNotificationWithBackStack(
        targetNotificationActivity: Class<*>?,
        channelId: String,
        title: String,
        text: String,
        bigText: String,
        priority: Int = NotificationCompat.PRIORITY_DEFAULT ,
        autoCancel: Boolean = true,
        notificationId: Int,
        pendingIntentFlag: Int,
        context: Context
    ) {
        val intent = Intent(context, targetNotificationActivity)
        val taskStackBuilder = TaskStackBuilder.create(
            context
        )
        taskStackBuilder.addNextIntentWithParentStack(intent)
        intent.putExtra("count", title)
        val pendingIntent = taskStackBuilder.getPendingIntent(0, pendingIntentFlag)
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, channelId).apply {
                setSmallIcon(R.drawable.ic_notification)
                setLargeIcon(
                    BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.ic_icon_large
                    )
                )
                setContentTitle(title)
                setContentText(text)
                setStyle(NotificationCompat.BigTextStyle().bigText(bigText))
                setPriority(priority)
                setAutoCancel(autoCancel)
                setContentIntent(pendingIntent)
                setChannelId(channelId)
                setOngoing(true)
            }


        val notificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManagerCompat.notify(notificationId, builder.build())
    }

    fun updateWithPicture(
        targetNotificationActivity: Class<*>?,
        title: String,
        text: String,
        channelId: String,
        notificationId: Int,
        bigpictureString: String,
        pendingIntentflag: Int,
        context: Context
    ) {
        val intent = Intent(context, targetNotificationActivity)
        intent.putExtra("count", title)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, pendingIntentflag)
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(
            context,
            channelId
        ).apply {
            setSmallIcon(R.drawable.ic_notification)
            setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_icon_large))
            setContentTitle(title)
            setContentText(text)
            setPriority(NotificationCompat.PRIORITY_DEFAULT)
            setContentIntent(pendingIntent)
            setChannelId(channelId)
            setAutoCancel(true)
        }

        val androidImage = BitmapFactory.decodeResource(context.resources, R.drawable.big_pic)
        builder.setStyle(
            NotificationCompat.BigPictureStyle().bigPicture(androidImage).bigLargeIcon(null)
                .setBigContentTitle(bigpictureString)
        )
        val notificationManager =
//            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            NotificationManagerCompat.from(context)

        notificationManager.notify(notificationId, builder.build())
    }

    fun cancelNotification(notificationId: Int, notificationManager: NotificationManagerCompat) {
        notificationManager.cancel(notificationId)
    }
}