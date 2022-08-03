package com.example.gomeltravelapp.presentation.mapScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gomeltravelapp.common.data
import com.example.gomeltravelapp.domain.model.LocationModel
import com.example.gomeltravelapp.domain.model.Place
import com.example.gomeltravelapp.domain.model.Point
import com.example.gomeltravelapp.domain.usecase.get_map.GetCurrentLocationUseCase
import com.example.gomeltravelapp.domain.usecase.get_map.GetPlacesOnMapUseCase
import com.example.gomeltravelapp.domain.usecase.get_map.MapVariant
import com.example.gomeltravelapp.presentation.util.WhileViewSubscribed
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

const val cameraUpdatesKey = "cameraUpdatesKey"

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@HiltViewModel
class MapViewModel @Inject constructor(
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val getPlacesOnMapUseCase: GetPlacesOnMapUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _errorMessage = Channel<String>(1, BufferOverflow.DROP_LATEST)
    val errorMessage: Flow<String> =
        _errorMessage.receiveAsFlow().shareIn(viewModelScope, WhileViewSubscribed)

    private val _mapCenterEvent = Channel<CameraUpdate>(Channel.CONFLATED)
    val mapCenterEvent = _mapCenterEvent.receiveAsFlow()

    private val _mapVariant = MutableStateFlow<MapVariant?>(null)
    val mapVariant: StateFlow<MapVariant?> = _mapVariant

    private val _locationData = MutableStateFlow(LocationState())
    val locationData: StateFlow<LocationState>
        get() = _locationData

    private val _placesList = MutableStateFlow<List<Place>>(emptyList())
    val placesList: StateFlow<List<Place>> = _placesList

    private val _state = MutableStateFlow(Placetate())
    val state: StateFlow<Placetate>
        get() = _state

    init {
        getCurrentLocation()
    }

    private fun getCurrentLocation() {
        val cameraUpdates: LatLng? = savedStateHandle[cameraUpdatesKey]
        cameraUpdates?.let {
            offerCameraUpdates(cameraUpdates)
        }
        viewModelScope.launch {
            getCurrentLocationUseCase(Unit).collect {
                _locationData.value = LocationState(point = it)
            }
        }
    }

     fun getPlacesOnMap(latLng: LatLng/*latLng: LatLng?*/) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getPlacesOnMapUseCase(Point(latLng.latitude, latLng.longitude)).collect {
                    if (it.data.isNullOrEmpty()) {
                        _state.value = Placetate(isEmpty = true)
                    } else {
                        _state.value = Placetate(places = it.data!!)
                    }
                }
            }
        }
    }

    private fun saveCurrentLatLng(latLng: LatLng) {
        savedStateHandle[cameraUpdatesKey] = latLng
    }

    private fun offerCameraUpdates(latLng: LatLng) {
        val update = CameraUpdateFactory.newLatLngZoom(latLng, 16.4f)
        _mapCenterEvent.trySend(update)
    }

    fun setMapVariant(variant: MapVariant) {
        _mapVariant.value = variant
    }
}

data class Placetate(
    val places: List<Place> = emptyList(),
    val icon: Int? = null,
    val isEmpty: Boolean = false,
    val isTypeSectionVisible: Boolean = false,
    val isSelected: Boolean = false
)

data class LocationState(
    val point: LocationModel? = null,
    val isEmpty: Boolean = false,
    val hasError: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = ""
)
