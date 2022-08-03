package com.example.gomeltravelapp.presentation
import com.example.gomeltravelapp.domain.model.Place

//Класс, который отвечает за состояние
data class PlaceState(
    val architecturePlaces: List<Place> = emptyList(),
    val historicPlaces: List<Place> = emptyList(),
    val urbanEnvironmentPlaces: List<Place> = emptyList(),
    val museumPlaces: List<Place> = emptyList(),
    val entertainmentPlaces: List<Place> = emptyList(),
    val religionPlaces: List<Place> = emptyList(),
    val otherPlaces: List<Place> = emptyList(),
    val allPlaces: List<Place> = architecturePlaces + historicPlaces + urbanEnvironmentPlaces +
            museumPlaces + entertainmentPlaces + religionPlaces,
    val isLoading: Boolean = false,
    val error: String = "",
    val isEmpty: Boolean = false
)
