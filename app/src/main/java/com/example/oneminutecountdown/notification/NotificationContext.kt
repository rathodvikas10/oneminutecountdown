package com.example.oneminutecountdown.notification

interface NotificationContext {
    val id: Int
    val title: String
    val text: String
}
class BaseNotification(
    override val id: Int,
    override val title: String,
    override val text: String
) : NotificationContext