package com.example.gomeltravelapp.data.repository

import com.example.gomeltravelapp.common.Resource
import com.example.gomeltravelapp.data.db.PlacesTableDao
import com.example.gomeltravelapp.domain.model.LocationModel
import com.example.gomeltravelapp.domain.model.Place
import com.example.gomeltravelapp.domain.model.Point
import com.example.gomeltravelapp.domain.remote.LocationRemoteDataSource
import com.example.gomeltravelapp.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

//Реализация репозитория местонохождения
class LocationRepositoryImpl @Inject constructor(
    private val dao: PlacesTableDao,
    private val locationData: LocationRemoteDataSource
) : LocationRepository {

    override suspend fun getLocationDataOnce(): LocationModel {
        return locationData.getCurrentLocationOnce()
    }

    override suspend fun getLocationDataLive(): Flow<LocationModel> {
        return locationData.getCurrentLocationLive()/*.map {
            Resource.Success(it)
        }*/
    }

    override fun getPlacesOnMap(point: Point): Flow<Resource<List<Place>>> {
        return dao.getPlacesOnMap(point).toListDataRecipeFlow()
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


