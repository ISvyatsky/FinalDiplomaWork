package com.example.gomeltravelapp.domain.usecase.get_map

import com.example.gomeltravelapp.common.Resource
import com.example.gomeltravelapp.di.IoDispatcher
import com.example.gomeltravelapp.domain.usecase.FlowUseCase
import com.example.gomeltravelapp.domain.model.Place
import com.example.gomeltravelapp.domain.model.Point
import com.example.gomeltravelapp.domain.repository.LocationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlacesOnMapUseCase @Inject constructor(
    private val repository: LocationRepository,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
) : FlowUseCase<Point, List<Place>>(ioDispatcher) {
    override fun execute(parameters: Point): Flow<Resource<List<Place>>> =
        repository.getPlacesOnMap(parameters)
}