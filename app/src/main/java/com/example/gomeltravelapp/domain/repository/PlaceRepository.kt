package com.example.gomeltravelapp.domain.repository

import com.example.gomeltravelapp.common.Resource
import com.example.gomeltravelapp.domain.model.Place
import com.example.gomeltravelapp.domain.model.Point
import kotlinx.coroutines.flow.Flow

//Определяем функции, которые должен иметь этот репозиторий достопримечательностей
interface PlaceRepository {
    fun getPlaces(): Flow<Resource<List<Place>>>
    fun getPlacesByType(type: String): Flow<Resource<List<Place>>>
    suspend fun getPlaceById(placeId: Int): Place
    suspend fun savePlace(id: Int?)
    suspend fun deleteSavedPlace(id: Int?)
    fun getSavedPlaces(): Flow<Resource<List<Place>>>
    suspend fun isPlaceSaved(id: Int?): Boolean
    fun searchPlaces(query: String): Flow<Resource<List<Place>>>
}

