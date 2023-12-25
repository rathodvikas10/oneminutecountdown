package com.example.oneminutecountdown.utils

private fun Long.padZero(desiredLength: Int) = this.toString().padStart(desiredLength, '0')

fun Long.toTextFormat(): String {
    val millisecondsFormatted = ((this / 10 or 0) % 100).padZero(2)
    val seconds = this / 1000
    val secondsFormatted = (seconds % 60).padZero(2)
    val minutes = seconds / 60
    val minutesFormatted = (minutes % 60).padZero(2)
    val hours = minutes / 60
    return if (hours > 0) {
        val hoursFormatted = (minutes / 60).padZero(2)
        "$hoursFormatted:$minutesFormatted:$secondsFormatted"
    } else {
        "$minutesFormatted:$secondsFormatted:$millisecondsFormatted"
    }
}