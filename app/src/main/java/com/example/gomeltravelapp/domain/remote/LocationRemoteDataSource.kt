package com.example.gomeltravelapp.domain.remote

import com.example.gomeltravelapp.domain.model.LocationModel
import kotlinx.coroutines.flow.Flow

interface LocationRemoteDataSource {
    suspend fun getCurrentLocationLive(): Flow<LocationModel>
    suspend fun getCurrentLocationOnce(): LocationModel
}
