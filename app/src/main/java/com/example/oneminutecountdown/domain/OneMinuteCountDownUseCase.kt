package com.example.oneminutecountdown.domain

import com.example.oneminutecountdown.domain.timer.OneMinuteTimer
import com.example.oneminutecountdown.domain.timer.TickerListener
import com.example.oneminutecountdown.utils.toTextFormat

class OneMinuteCountDownUseCase {

    private var mCallback: ((ProgressInfo) -> Unit)? = null

    private val mOneMinuteTimer = OneMinuteTimer(
        mTickerListener = object : TickerListener {
            override fun onTick() {
                mCallback?.invoke(getProgressInfo())
            }

            override fun onFinish() {
                mCallback?.invoke(getProgressInfo(true))
            }
        }
    )

    fun play(onProgressInfo: (ProgressInfo) -> Unit) {
        mCallback = onProgressInfo
        mOneMinuteTimer.play()
    }

    fun pause() {
        mOneMinuteTimer.pause()
    }

    fun stop() {
        mOneMinuteTimer.stop()
    }

    private fun getProgressInfo(finish: Boolean = false): ProgressInfo {
        val progress = (mOneMinuteTimer.getElapsedTime().toFloat() / OneMinuteTimer.DEFAULT_DURATION.toFloat()).times(100f)
        val tickerText = mOneMinuteTimer.getElapsedTime().toTextFormat()
        return ProgressInfo(progress, tickerText, finish)
    }

}