package com.example.oneminutecountdown.ui.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable

@Composable
fun CircularButton(
    onClick: () -> Unit,
    icon: @Composable () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        shape = CircleShape,
    ) {
        icon()
    }
}