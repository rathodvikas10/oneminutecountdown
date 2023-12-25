package com.example.oneminutecountdown.ui.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.oneminutecountdown.ui.components.CircularButton
import com.example.oneminutecountdown.ui.components.CountDownWidget
import com.example.oneminutecountdown.ui.components.DisposableLifecycleObserver
import com.example.oneminutecountdown.viewmodel.OneMinuteCountDownViewModel
import com.example.oneminutecountdown.viewmodel.UIState.TimerState.*
import org.koin.androidx.compose.koinViewModel

@Composable
fun OneMinuteCountDownScreen(
    viewModel: OneMinuteCountDownViewModel = koinViewModel()
) {
    
    DisposableLifecycleObserver(viewModel)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        val uiState by viewModel.uiState.collectAsState()

        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CountDownWidget(
                modifier = Modifier
                    .weight(1f)
                    .size(250.dp),
                circleRadius = 230f,
                progress = uiState.progress,
                tickerText = uiState.tickerText
            )

            Row(
                Modifier
                    .wrapContentSize()
                    .animateContentSize(animationSpec = tween())
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                when(uiState.timerState) {
                    IDEAL, STOPPED -> {
                        StartButton(viewModel)
                    }
                    RUNNING -> {
                        PauseButton(viewModel)
                        Spacer(modifier = Modifier.width(26.dp))
                        StopButton(viewModel)
                    }
                    PAUSED -> {
                        StartButton(viewModel)
                        Spacer(modifier = Modifier.width(26.dp))
                        StopButton(viewModel)
                    }
                }
            }
        }
    }
}

@Composable
private fun StartButton(viewModel: OneMinuteCountDownViewModel) {
    CircularButton(
        onClick = { viewModel.start() }
    ) {
        Icon(Icons.Filled.PlayArrow, "Start timer")
    }
}

@Composable
private fun StopButton(viewModel: OneMinuteCountDownViewModel) {
    CircularButton(
        onClick = { viewModel.stop() },
    ) {
        Icon(Icons.Filled.Stop, "Stop timer")
    }
}

@Composable
private fun PauseButton(viewModel: OneMinuteCountDownViewModel) {
    CircularButton(
        onClick = { viewModel.pause() },
    ) {
        Icon(Icons.Filled.Pause, "Pause timer")
    }
}
