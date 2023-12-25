package com.example.oneminutecountdown.viewmodel

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oneminutecountdown.domain.OneMinuteCountDownUseCase
import com.example.oneminutecountdown.domain.ProgressInfo
import com.example.oneminutecountdown.notification.NotificationController
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OneMinuteCountDownViewModel(
    private val oneMinuteCountDown: OneMinuteCountDownUseCase,
    private val notificationController: NotificationController,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel(), DefaultLifecycleObserver {

    private val mScope = CoroutineScope(dispatcher + SupervisorJob())
    private val _uiState = MutableStateFlow(UIState.INITIAL)
    val uiState = _uiState

    private var isInBackground = false

    fun start() {
        viewModelScope
        mScope.launch(dispatcher) {
            oneMinuteCountDown.play { progressInfo ->
                _uiState.tryEmit(progressInfo.toUIState())
                if(progressInfo.finish) {
                    showNotificationWhenInBackground()
                }
            }
        }
    }

    fun pause() {
        viewModelScope.launch(dispatcher) {
            oneMinuteCountDown.pause()
            _uiState.update { current ->
                current.copy(
                    timerState = UIState.TimerState.PAUSED
                )
            }
        }
    }

    fun stop() {
        viewModelScope.launch(dispatcher) {
            oneMinuteCountDown.stop()
            _uiState.update { current ->
                current.copy(
                    timerState = UIState.TimerState.STOPPED,
                    progress = 0f,
                    tickerText = "00:00:00"
                )
            }
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        isInBackground = true
    }

    override fun onResume(owner: LifecycleOwner) {
        isInBackground = false
    }

    private fun showNotificationWhenInBackground() {
        if(isInBackground) {
            notificationController.sendTimeUpNotification()
        }
    }

    private fun ProgressInfo.toUIState() = UIState.CountDownState(
        timerState = if(progress >= 100F) UIState.TimerState.IDEAL else UIState.TimerState.RUNNING,
        progress = progress,
        tickerText = tickerText
    )
}