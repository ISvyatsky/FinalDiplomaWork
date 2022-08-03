package com.example.gomeltravelapp.presentation

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gomeltravelapp.R
import com.example.gomeltravelapp.common.data
import com.example.gomeltravelapp.domain.model.Place
import com.example.gomeltravelapp.domain.usecase.get_bookmark.DeletePlaceSuspendUseCase
import com.example.gomeltravelapp.domain.usecase.get_bookmark.IsPlaceSavedSuspendUseCase
import com.example.gomeltravelapp.domain.usecase.get_bookmark.SavePlaceSuspendUseCase
import com.example.gomeltravelapp.domain.usecase.get_place.GetPlaceUseCase
import com.example.gomeltravelapp.domain.usecase.search.SearchPlacesUseCase
import com.example.gomeltravelapp.presentation.mapScreen.MapEvent
import com.example.gomeltravelapp.presentation.mapScreen.Placetate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PlaceViewModel @Inject constructor(
    private val getPlaceUseCase: GetPlaceUseCase,
    private val searchPlacesUseCase: SearchPlacesUseCase,
    private val savePlaceUseCase: SavePlaceSuspendUseCase,
    private val deletePlaceUseCase: DeletePlaceSuspendUseCase,
    private val isPlaceSavedUseCase: IsPlaceSavedSuspendUseCase,
): ViewModel() {

    private val _state = MutableStateFlow(PlaceState())
    val state: StateFlow<PlaceState>
        get() = _state

    private val _searchState = MutableStateFlow(SearchPlaceState())
    val searchState: StateFlow<SearchPlaceState>
        get() = _searchState

    val hasError = MutableStateFlow(false)
    val loading = MutableStateFlow(false)

    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private val _searchTextState: MutableState<String> =
        mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    var placeMapState by mutableStateOf(Placetate())

    /*private val _placeMapState = MutableStateFlow(Placetate())
    val placeMapState: StateFlow<Placetate>
        get() = _placeMapState*/

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

    init {
        getPlaceList()
    }

    fun onEvent(event: MapEvent) {
        when(event) {
            is MapEvent.OnArchitectureButtonClick -> {
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        getPlaceUseCase("%architecture%").collect {
                            placeMapState = placeMapState.copy(places= it.data!!, icon = R.drawable.ic_arch)
                            event.places = it.data!!
                        }
                    }
                }
            }
            is MapEvent.OnHistoricButtonClick -> {
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        getPlaceUseCase("%historic%").collect {
                            placeMapState = placeMapState.copy(places= it.data!!, icon = R.drawable.ic_hist)
                            event.places = it.data!!
                        }
                    }
                }
            }
            is MapEvent.OnUrbanButtonClick -> {
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        getPlaceUseCase("%urban_environment%").collect {
                            placeMapState = placeMapState.copy(places= it.data!!, icon = R.drawable.ic_urban)
                            event.places = it.data!!
                        }
                    }
                }
            }
            is MapEvent.OnMuseumButtonClick -> {
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        getPlaceUseCase("%museums%").collect {
                            placeMapState = placeMapState.copy(places= it.data!!, icon = R.drawable.ic_museum)
                            event.places = it.data!!
                        }
                    }
                }
            }
            is MapEvent.OnEntertainmentButtonClick -> {
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        getPlaceUseCase("%theatres_and_entertainments%").collect {
                            placeMapState = placeMapState.copy(places= it.data!!, icon = R.drawable.ic_theatre)
                            event.places = it.data!!
                        }
                    }
                }
            }
            is MapEvent.OnReligionButtonClick -> {
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        getPlaceUseCase("%religion%").collect {
                            placeMapState = placeMapState.copy(places= it.data!!, icon = R.drawable.ic_church)
                            event.places = it.data!!
                        }
                    }
                }
            }
            is MapEvent.OnAllButtonClick -> {
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        getPlaceUseCase().collect {
                            placeMapState = placeMapState.copy(places= it.data!!, icon = R.drawable.ic_alll)
                            event.places = it.data!!
                        }
                    }
                }
            }
            is MapEvent.ToggleTypeSection -> {
                placeMapState = placeMapState.copy(
                    isTypeSectionVisible = !placeMapState.isTypeSectionVisible)
            }
        }
    }

    private fun getPlaceList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                newCombine(
                    getPlaceUseCase("%architecture%"),
                    getPlaceUseCase("%historic%"),
                    getPlaceUseCase("%urban_environment%"),
                    getPlaceUseCase("%museums%"),
                    getPlaceUseCase("%theatres_and_entertainments%"),
                    getPlaceUseCase("%religion%"),
                    getPlaceUseCase("%architecture%"),
                    getPlaceUseCase("%religion%")
                ) {
                        architecturePlaces, historicPlaces, urbanEnvironmentPlaces,
                        museumPlaces, entertainmentPlaces, religionPlaces, allPlaces, otherPlaces ->

                    PlaceState(
                        architecturePlaces = architecturePlaces.data ?: emptyList(),
                        historicPlaces = historicPlaces.data ?: emptyList(),
                        urbanEnvironmentPlaces = urbanEnvironmentPlaces.data ?: emptyList(),
                        museumPlaces = museumPlaces.data ?: emptyList(),
                        entertainmentPlaces = entertainmentPlaces.data ?: emptyList(),
                        religionPlaces = religionPlaces.data ?: emptyList(),
                        allPlaces = architecturePlaces.data!! + historicPlaces.data!! +
                                urbanEnvironmentPlaces.data!! + museumPlaces.data!! +
                                entertainmentPlaces.data!! + religionPlaces.data!!,
                        otherPlaces = otherPlaces.data ?: emptyList()
                    )
                }.collect {
                    _state.value = it
                }
            }
        }
    }

    fun searchPlaces(query: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                searchPlacesUseCase(query).collect {
                    _searchState.value = SearchPlaceState(places = it.data!!)
                }
            }
        }
    }

    data class SearchPlaceState(
        val places: List<Place> = emptyList(),
        val isEmpty: Boolean = false,
        val searchQuery: String = ""
    )

    fun onBookMark(place: Place) {
        viewModelScope.launch {
            if (isPlaceSavedUseCase(place.id).data == true) {
                deletePlaceUseCase(place.id)
            } else {
                savePlaceUseCase(place.id)
            }
        }
    }

    private fun <T1, T2, T3, T4, T5, T6, T7, T8, R> newCombine(
        flow: Flow<T1>,
        flow2: Flow<T2>,
        flow3: Flow<T3>,
        flow4: Flow<T4>,
        flow5: Flow<T5>,
        flow6: Flow<T6>,
        flow7: Flow<T7>,
        flow8: Flow<T8>,
        transform: suspend (T1, T2, T3, T4, T5, T6, T7, T8) -> R
    ): Flow<R> = combine(
        combine(flow, flow2, flow3, ::Triple),
        combine(flow4, flow5, flow6, ::Triple),
        combine(flow7, flow8, ::Pair)
    ) { t1, t2, t3 ->
        transform(
            t1.first,
            t1.second,
            t1.third,
            t2.first,
            t2.second,
            t2.third,
            t3.first,
            t3.second
        )
    }
}


