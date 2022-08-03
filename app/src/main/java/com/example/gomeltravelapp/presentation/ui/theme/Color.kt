package com.example.gomeltravelapp.presentation.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

val white = Color(0xffffffff)
val black = Color(0xff000000)
val paleWhite = Color(0xfff3f7f9)
val paleBlack = Color(0xff222325)
val blue = Color(148, 166, 254, 255)
val blueLight = Color(211, 217, 252, 255)
val ghostWhite = Color(245, 246, 255)
val platinum = Color(200, 202, 204)
val lightSilver = Color(171, 172, 173)
val gold = Color(238, 141, 60, 255)
val navajoWhite = Color(254, 228, 162)
val water = Color(220, 237, 255)
val lightBlue = Color(229, 240, 252)

val seashell = Color(253, 246, 235)
val aliceBlue = Color(237, 246, 255)
val cultured = Color(244, 246, 248)
val azureishWhite = Color(220, 237, 247)

@Composable
fun Colors.compositedOnSurface(alpha: Float): Color {
    return onSurface.copy(alpha = alpha).compositeOver(surface)
}

@Composable
fun Colors.randomBackgroundColor(): Color {
    val colors: List<Color> = listOf(
        Color(0xFFD0EFB3),
        Color(0xFFC0D6E3),
        Color(0xFFD8D8D8),
        Color(0xFFEE7B7E),
        Color(0xFFFFD48F),
        Color(0xFFD8D8D8)
    )

    return colors.random()
}
