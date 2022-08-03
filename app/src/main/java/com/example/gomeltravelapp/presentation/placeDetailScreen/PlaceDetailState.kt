package com.example.gomeltravelapp.presentation.placeDetailScreen

import com.example.gomeltravelapp.domain.model.Place

//Класс, который отвечает за состояние
data class PlaceDetailState(

    val place: Place? = null,
    val isEmpty: Boolean = false,
    val hasError: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = ""
)
