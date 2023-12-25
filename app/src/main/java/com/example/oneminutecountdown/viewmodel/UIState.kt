package com.example.oneminutecountdown.viewmodel

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