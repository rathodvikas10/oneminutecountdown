package com.example.oneminutecountdown.di

import com.example.oneminutecountdown.domain.OneMinuteCountDownUseCase
import com.example.oneminutecountdown.notification.AppNotificationService
import com.example.oneminutecountdown.notification.NotificationController
import com.example.oneminutecountdown.notification.NotificationService
import com.example.oneminutecountdown.viewmodel.OneMinuteCountDownViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<NotificationService> { AppNotificationService(get()) }
    factory { NotificationController(get()) }

    factory { OneMinuteCountDownUseCase() }

    viewModel {
        OneMinuteCountDownViewModel(
            get(),
            get(),
            Dispatchers.IO,
        )
    }
}