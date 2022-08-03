package com.example.gomeltravelapp.presentation.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.gomeltravelapp.R

private val dmSans = FontFamily(
    Font(R.font.mazzardregular),
    Font(R.font.mazzardmedium, FontWeight.W500),
    Font(R.font.mazzardbold, FontWeight.Bold)
)


val typography = Typography(defaultFontFamily = dmSans)