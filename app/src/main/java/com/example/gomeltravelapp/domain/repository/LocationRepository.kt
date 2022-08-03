package com.example.gomeltravelapp.domain.repository

import com.example.gomeltravelapp.common.Resource
import com.example.gomeltravelapp.domain.model.LocationModel
import com.example.gomeltravelapp.domain.model.Place
import com.example.gomeltravelapp.domain.model.Point
import kotlinx.coroutines.flow.Flow

//Определяем функции, которые должен иметь репозиторий местоположения
interface LocationRepository {
    suspend fun getLocationDataOnce(): LocationModel
    suspend fun getLocationDataLive(): Flow<LocationModel>
    fun getPlacesOnMap(point: Point): Flow<Resource<List<Place>>>
}
