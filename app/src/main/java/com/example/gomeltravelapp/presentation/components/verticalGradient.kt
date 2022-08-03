package com.example.gomeltravelapp.presentation.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun Modifier.verticalGradient(): Modifier = composed {
    val color = Color.Black.copy(alpha = 0.3f)
    val colors = remember { listOf(color.copy(alpha = 0f), color) }

    var height by remember { mutableStateOf(0f) }
    val brush = remember(color, height) {
        Brush.verticalGradient(
            colors = colors,
            startY = height * 1f,
            endY = height * 0f
        )
    }

    drawBehind {
        height = size.height
        drawRect(brush = brush)
    }
}
