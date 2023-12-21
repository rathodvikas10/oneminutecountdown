package com.example.oneminutecountdown.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import com.example.oneminutecountdown.ui.components.CountDownWidget
import com.example.oneminutecountdown.ui.theme.darkGray
import com.example.oneminutecountdown.ui.theme.gray
import com.example.oneminutecountdown.ui.theme.orange
import com.example.oneminutecountdown.utils.disposableLifecycleObserver
import com.example.oneminutecountdown.viewmodel.OneMinuteCountDownViewModel
import com.example.oneminutecountdown.viewmodel.UIState
import org.koin.androidx.compose.koinViewModel
import kotlin.time.Duration.Companion.minutes

@Composable
fun OneMinuteCountDownScreen(
    viewModel: OneMinuteCountDownViewModel = koinViewModel()
) {
    
    viewModel.disposableLifecycleObserver(lifecycle = LocalLifecycleOwner.current.lifecycle)

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
                primaryColor = orange,
                secondaryColor = gray,
                modifier = Modifier
                    .weight(1f)
                    .size(250.dp)
                    .background(darkGray),
                circleRadius = 230f,
                progress = uiState.progress,
                tickerText = uiState.tickerText
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                when(uiState.timerState) {
                    UIState.TimerState.IDEAL, UIState.TimerState.STOPPED -> {
                        Button(onClick = { viewModel.start() }) {
                            Text(text = "Start")
                        }
                    }
                    UIState.TimerState.RUNNING -> {
                        Button(onClick = { viewModel.pause() }) {
                            Text(text = "Pause")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(onClick = { viewModel.stop() }) {
                            Text(text = "Stop")
                        }
                    }
                    UIState.TimerState.PAUSED -> {
                        Button(onClick = { viewModel.start() }) {
                            Text(text = "Start")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(onClick = { viewModel.stop() }) {
                            Text(text = "Stop")
                        }
                    }
                }
            }
        }
    }
}