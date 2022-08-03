package com.example.gomeltravelapp.domain.usecase.get_map

import com.example.gomeltravelapp.di.IoDispatcher
import com.example.gomeltravelapp.domain.usecase.SuspendFlowUseCase
import com.example.gomeltravelapp.domain.model.LocationModel
import com.example.gomeltravelapp.domain.repository.LocationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
) : SuspendFlowUseCase<Unit, LocationModel>(ioDispatcher) {

    override suspend fun execute(parameters: Unit): Flow<LocationModel> =
        locationRepository.getLocationDataLive()
}



