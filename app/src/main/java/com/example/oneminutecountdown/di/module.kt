package com.example.oneminutecountdown.di


import com.example.oneminutecountdown.domain.OneMinuteTimer
import com.example.oneminutecountdown.notification.AppNotificationSender
import com.example.oneminutecountdown.notification.CountDownNotificationController
import com.example.oneminutecountdown.notification.NotificationSender
import com.example.oneminutecountdown.viewmodel.OneMinuteCountDownViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory { OneMinuteTimer() }
    single<NotificationSender> { AppNotificationSender(get()) }
    factory { CountDownNotificationController(get()) }
    viewModel {
        OneMinuteCountDownViewModel(
            get(),
            Dispatchers.IO,
            get()
        )
    }
}