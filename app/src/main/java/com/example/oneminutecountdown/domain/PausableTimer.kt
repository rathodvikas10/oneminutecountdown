package com.example.oneminutecountdown.domain

interface PausableTimer {
    fun play()
    fun pause()
    fun stop()
    fun getRemainingTime() : Long
    fun getElapsedTime() : Long
}