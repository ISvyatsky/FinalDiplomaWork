package com.example.gomeltravelapp.presentation.placeDetailScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gomeltravelapp.common.data
import com.example.gomeltravelapp.domain.model.Place
import com.example.gomeltravelapp.domain.usecase.get_bookmark.SavePlaceSuspendUseCase
import com.example.gomeltravelapp.domain.usecase.get_detail_place.GetPlaceDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceDetailViewModel @Inject constructor(
    private val getPlaceDetailUseCase: GetPlaceDetailUseCase,
    private val savePlaceUseCase: SavePlaceSuspendUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val isLoading = MutableStateFlow(false)
    private val _state = MutableStateFlow(PlaceDetailState())
    val state: StateFlow<PlaceDetailState>
        get() = _state

    init {
        savedStateHandle.get<Int>("id")?.let { id ->
            getPlaceDetail(id)
        }
    }

    private fun getPlaceDetail(id: Int) {
        viewModelScope.launch {
            _state.value = PlaceDetailState(place = getPlaceDetailUseCase(id).data)
        }
    }

    fun savePlace(place: Place) {
        viewModelScope.launch {
            savePlaceUseCase(place.id)
        }
    }
}
