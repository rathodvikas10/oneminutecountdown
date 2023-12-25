package com.example.oneminutecountdown.domain.timer

import java.util.Timer
import kotlin.concurrent.fixedRateTimer
import kotlin.concurrent.timer

class OneMinuteTimer(
    private val mTickerListener: TickerListener
) : AppTimer {
    companion object {
        const val DEFAULT_INTERVAL = 10L
        const val DEFAULT_DURATION = 60000L
    }

    private val interval: Long = DEFAULT_INTERVAL
    private val duration: Long = DEFAULT_DURATION

    @Volatile
    private var mIsRunning = false
    @Volatile
    private var mElapsedTime: Long = 0
    private var mTimer: Timer? = null

    override fun play() {
        if (mIsRunning.not()) {
            startTimer()
        }
    }

    override fun stop() {
        cancelTimer()
        mElapsedTime = 0
    }

    override fun pause() {
        cancelTimer()
    }

    override fun getRemainingTime() = duration - mElapsedTime
    override fun getElapsedTime() = mElapsedTime

    // start timer
    private fun startTimer() {
        mIsRunning = true
        mTimer = fixedRateTimer(period = interval) {
            mElapsedTime += interval
            mTickerListener.onTick()
            if (mElapsedTime >= duration) {
                mTickerListener.onFinish()
                stop()
            }
        }
    }

    // cancel timer
    private fun cancelTimer() {
        if (mIsRunning) {
            mTimer?.cancel()
            mTimer = null
            mIsRunning = false
        }
    }
}