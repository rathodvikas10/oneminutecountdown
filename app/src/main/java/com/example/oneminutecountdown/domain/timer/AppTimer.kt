package com.example.oneminutecountdown.domain.timer

interface AppTimer {
    fun play()
    fun pause()
    fun stop()
    fun getRemainingTime() : Long
    fun getElapsedTime() : Long
}