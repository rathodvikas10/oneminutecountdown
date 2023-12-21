package com.example.oneminutecountdown.domain

interface CountDownListener {
    fun onTick()
    fun onFinish()
}