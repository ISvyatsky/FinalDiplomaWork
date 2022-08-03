package com.example.gomeltravelapp.presentation.mapScreen

import com.example.gomeltravelapp.domain.model.Place

sealed class MapEvent {
    data class OnArchitectureButtonClick(var places: List<Place>): MapEvent()
    data class OnHistoricButtonClick(var places: List<Place>): MapEvent()
    data class OnUrbanButtonClick(var places: List<Place>): MapEvent()
    data class OnMuseumButtonClick(var places: List<Place>): MapEvent()
    data class OnEntertainmentButtonClick(var places: List<Place>): MapEvent()
    data class OnReligionButtonClick(var places: List<Place>): MapEvent()
    data class OnAllButtonClick(var places: List<Place>): MapEvent()
    object ToggleTypeSection: MapEvent()
}
