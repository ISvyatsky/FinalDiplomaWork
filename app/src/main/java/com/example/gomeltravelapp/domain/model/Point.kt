package com.example.gomeltravelapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//Модель координаты
@Parcelize
data class Point(
    val lat: Double,
    val lon: Double
): Parcelable