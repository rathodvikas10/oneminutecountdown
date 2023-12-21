package com.example.oneminutecountdown.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Timer
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration.Companion.minutes

class OneMinuteTimer(
    private val interval: Long = 10,
    val duration: Long = 1.minutes.inWholeMilliseconds,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) : PausableTimer {

    @Volatile private var isRunning = false
    @Volatile private var elapsedTime: Long = 0
    private var countDownListener: CountDownListener? = null
    private var timer: Timer? = null

    private val scope = CoroutineScope(dispatcher)

    fun setOnCountDownListener(listener: CountDownListener) {
        this.countDownListener = listener
    }

    override fun play() {
        if(isRunning) {
            return
        } else {
            isRunning = true
            scope.launch {
                timer = fixedRateTimer(daemon = true, period = interval) {
                    elapsedTime += interval
                    countDownListener?.onTick()
                    if(elapsedTime >= duration){
                        countDownListener?.onFinish()
                        isRunning = false
                        timer?.cancel()
                        timer = null
                        elapsedTime = 0
                    }
                }
            }
        }
    }

    override fun stop() {
        pause()
        elapsedTime = 0
    }

    override fun pause() {
        if(isRunning) {
            timer?.cancel()
            timer = null
            isRunning = false
        }
    }

    override fun getRemainingTime() = duration - elapsedTime
    override fun getElapsedTime() = elapsedTime
}