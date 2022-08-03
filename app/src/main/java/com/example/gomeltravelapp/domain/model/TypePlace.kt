package com.example.gomeltravelapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//Сущность типа места
@Parcelize
data class TypePlace(
    val name: String,
    val countPlaces: Int,
    val image: Int,
    val results: List<Place> = emptyList(),
): Parcelable
