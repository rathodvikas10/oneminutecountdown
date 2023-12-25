package com.example.oneminutecountdown.domain.timer

interface TickerListener {
    fun onTick()
    fun onFinish()
}