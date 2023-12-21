package com.example.oneminutecountdown.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver

private fun Long.pad(desiredLength: Int) = this.toString().padStart(desiredLength, '0')

fun Long.format(): String {
    val millisecondsFormatted = ((this / 10 or 0) % 100).pad(2)
    val seconds = this / 1000
    val secondsFormatted = (seconds % 60).pad(2)
    val minutes = seconds / 60
    val minutesFormatted = (minutes % 60).pad(2)
    val hours = minutes / 60
    return if (hours > 0) {
        val hoursFormatted = (minutes / 60).pad(2)
        "$hoursFormatted:$minutesFormatted:$secondsFormatted"
    } else {
        "$minutesFormatted:$secondsFormatted:$millisecondsFormatted"
    }
}

@Composable
fun <LO : LifecycleObserver> LO.disposableLifecycleObserver(lifecycle: Lifecycle) {
    DisposableEffect(lifecycle) {
        lifecycle.addObserver(this@disposableLifecycleObserver)
        onDispose {
            lifecycle.removeObserver(this@disposableLifecycleObserver)
        }
    }
}