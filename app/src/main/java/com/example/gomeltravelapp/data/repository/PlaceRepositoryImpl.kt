package com.example.gomeltravelapp.data.repository

import com.example.gomeltravelapp.common.Resource
import com.example.gomeltravelapp.data.db.PlacesTableDao
import com.example.gomeltravelapp.domain.model.Place
import com.example.gomeltravelapp.domain.repository.PlaceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

//Реализация репозитория достопримечательностей
class PlaceRepositoryImpl @Inject constructor(
    private val dao: PlacesTableDao
) : PlaceRepository {

    override fun getPlaces(): Flow<Resource<List<Place>>>{
        return dao.getPlaces().toListDataRecipeFlow()
    }

    override fun getPlacesByType(type: String): Flow<Resource<List<Place>>> {
        return dao.getPlacesByType(type).toListDataRecipeFlow()
    }

    override suspend fun getPlaceById(placeId: Int): Place {
        return dao.getPlaceById(placeId)
    }

    override suspend fun savePlace(id: Int?) {
        withContext(Dispatchers.IO) {
            dao.savePlace(id)
        }
    }

    override suspend fun deleteSavedPlace(id: Int?) {
        return dao.deleteSavedPlace(id)
    }

    override fun getSavedPlaces(): Flow<Resource<List<Place>>> {
        return dao.getSavedPlaces().toListDataRecipeFlow()
    }

    override suspend fun isPlaceSaved(id: Int?): Boolean {
        return dao.isPlaceSaved(id)
    }

    override fun searchPlaces(query: String): Flow<Resource<List<Place>>> {
        val wildcardQuery = String.format("*%s*", query)
        return dao.searchPlaces(wildcardQuery).toListDataRecipeFlow()
    }


    private fun Flow<List<Place>>.toListDataRecipeFlow(): Flow<Resource<List<Place>>> {
        return this.map { items ->
            Resource.Success(items.toDataPlaces())
        }
    }

    private fun List<Place>.toDataPlaces(): List<Place> {
        return this.map {
            it
        }
    }
}
