package com.example.oneminutecountdown.notification

class CountDownNotificationController(
    private val notificationSender: NotificationSender
) {
    fun sendTimeUpNotification() {
        val notificationContext = BaseNotification(1,"Time is up!", "One minute count down is completed.")
        notificationSender.send(notificationContext)
    }
}