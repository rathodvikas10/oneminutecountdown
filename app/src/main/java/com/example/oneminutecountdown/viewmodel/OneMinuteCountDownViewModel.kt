package com.example.oneminutecountdown.viewmodel

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oneminutecountdown.domain.CountDownListener
import com.example.oneminutecountdown.domain.OneMinuteTimer
import com.example.oneminutecountdown.notification.CountDownNotificationController
import com.example.oneminutecountdown.utils.format
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OneMinuteCountDownViewModel(
    private val oneMinuteTimer: OneMinuteTimer,
    private val dispatcher: CoroutineDispatcher,
    private val countDownNotificationController: CountDownNotificationController
) : ViewModel(), CountDownListener, DefaultLifecycleObserver {

    private val scope = CoroutineScope(dispatcher)
    private val _uiState = MutableStateFlow(UIState.INITIAL)
    val uiState = _uiState

    private var isInBackground = false

    init {
        oneMinuteTimer.setOnCountDownListener(this)
    }

    fun start() {
        scope.launch(dispatcher) {
            oneMinuteTimer.play()
            _uiState.update { current ->
                current.copy(
                    timerState = UIState.TimerState.RUNNING
                )
            }
        }
    }

    fun pause() {
        viewModelScope.launch(dispatcher) {
            oneMinuteTimer.pause()
            _uiState.update { current ->
                current.copy(
                    timerState = UIState.TimerState.PAUSED
                )
            }
        }
    }

    fun stop() {
        viewModelScope.launch(dispatcher) {
            oneMinuteTimer.stop()
            _uiState.update { current ->
                current.copy(
                    timerState = UIState.TimerState.STOPPED,
                    progress = 0f,
                    tickerText = "00:00:00"
                )
            }
        }
    }

    override fun onTick() {
        scope.launch(dispatcher) {
            _uiState.update { current ->
                current.copy(
                    progress = (oneMinuteTimer.getElapsedTime().toFloat() / oneMinuteTimer.duration.toFloat()) * 100f,
                    tickerText = oneMinuteTimer.getRemainingTime().format()
                )
            }
        }
    }

    override fun onFinish() {
        scope.launch(dispatcher) {
            _uiState.update { current ->
                current.copy(
                    timerState = UIState.TimerState.IDEAL,
                    progress = (oneMinuteTimer.getElapsedTime().toFloat() / oneMinuteTimer.duration.toFloat()) * 100f,
                    tickerText = oneMinuteTimer.getRemainingTime().format()
                )
            }
            if(isInBackground) {
                countDownNotificationController.sendTimeUpNotification()
            }
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        isInBackground = true
    }

    override fun onResume(owner: LifecycleOwner) {
        isInBackground = false
    }
}

sealed class UIState {
    enum class TimerState {
        IDEAL, RUNNING, PAUSED, STOPPED
    }
    companion object {
        val INITIAL = CountDownState(
            timerState = TimerState.IDEAL,
            progress = 0f,
            tickerText = "00:00:00"
        )
    }
    data class CountDownState(
        val timerState: TimerState,
        val progress: Float,
        val tickerText: String
    ) : UIState()
}