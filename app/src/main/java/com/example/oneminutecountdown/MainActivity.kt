package com.example.oneminutecountdown

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.oneminutecountdown.ui.screen.OneMinuteCountDownScreen
import com.example.oneminutecountdown.ui.theme.OneMinuteCountDownTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OneMinuteCountDownTheme {
                OneMinuteCountDownScreen()
            }
        }
    }
}