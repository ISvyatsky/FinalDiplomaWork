package com.example.gomeltravelapp.domain.usecase.get_place

import com.example.gomeltravelapp.common.Resource
import com.example.gomeltravelapp.domain.model.Place
import com.example.gomeltravelapp.domain.repository.PlaceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlaceUseCase @Inject constructor(
    private val repository: PlaceRepository
) {
    //функции выполнения данного варианта использования
    operator fun invoke(type: String): Flow<Resource<List<Place>>> = repository.getPlacesByType(type)
    operator fun invoke(): Flow<Resource<List<Place>>> = repository.getPlaces()
}

