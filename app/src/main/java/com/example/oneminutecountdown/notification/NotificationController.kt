package com.example.oneminutecountdown.notification

class NotificationController(
    private val notificationService: NotificationService
) {
    fun sendTimeUpNotification() {
        val notificationContext = BaseNotification(1,"Time is up!", "One minute count down is completed.")
        notificationService.send(notificationContext)
    }
}