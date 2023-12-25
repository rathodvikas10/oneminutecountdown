package com.example.oneminutecountdown.notification

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.oneminutecountdown.R


interface NotificationService {
    fun send(notification: NotificationContext)
}

class AppNotificationService(
    private val context: Context,
) : NotificationService {
    override fun send(notification: NotificationContext) {
        val builder = NotificationCompat.Builder(context, NotificationUtils.CHANNEL_ID)
        builder.setContentTitle(notification.title)
        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
        builder.setContentText(notification.text)
        builder.setAutoCancel(true)
        val notificationManager = NotificationManagerCompat.from(context)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(context, "Notification Permission not granted", Toast.LENGTH_SHORT).show()
            return
        }
        notificationManager.notify(notification.id, builder.build())
    }


}