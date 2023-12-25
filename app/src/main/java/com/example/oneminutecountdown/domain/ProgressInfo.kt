package com.example.oneminutecountdown.domain

data class ProgressInfo(
    val progress: Float,
    val tickerText: String,
    val finish: Boolean = false
)