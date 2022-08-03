package com.example.gomeltravelapp.presentation.bookmarkScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gomeltravelapp.common.data
import com.example.gomeltravelapp.domain.model.Place
import com.example.gomeltravelapp.domain.usecase.get_bookmark.DeletePlaceSuspendUseCase
import com.example.gomeltravelapp.domain.usecase.get_bookmark.GetSavedPlacesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val getSavedPlacesUseCase: GetSavedPlacesUseCase,
    private val deletePlaceUseCase: DeletePlaceSuspendUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(BookmarkViewState())
    val state: StateFlow<BookmarkViewState>
        get() = _state

    init {
        getSavedRecipes()
    }

    private fun getSavedRecipes() {
        viewModelScope.launch {
            getSavedPlacesUseCase(Unit).collect {
                if (it.data.isNullOrEmpty()) {
                    _state.value = BookmarkViewState(isEmpty = true)
                } else {
                    _state.value = BookmarkViewState(places = it.data!!)
                }
            }
        }
    }

    fun deletePlace(place: Place) {
        viewModelScope.launch {
            deletePlaceUseCase(place.id)
        }
    }

    data class BookmarkViewState(
        val places: List<Place> = emptyList(),
        val isEmpty: Boolean = false
    )
}
