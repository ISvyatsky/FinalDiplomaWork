package com.example.gomeltravelapp.domain.usecase.search

import com.example.gomeltravelapp.common.Resource
import com.example.gomeltravelapp.domain.model.Place
import com.example.gomeltravelapp.domain.repository.PlaceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchPlacesUseCase @Inject constructor(
     private val repository: PlaceRepository
) {
     operator fun invoke(query: String): Flow<Resource<List<Place>>> = repository.searchPlaces(query)
}

