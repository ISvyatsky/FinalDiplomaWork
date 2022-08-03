package com.example.gomeltravelapp.domain.usecase.get_bookmark

import com.example.gomeltravelapp.common.Resource
import com.example.gomeltravelapp.di.IoDispatcher
import com.example.gomeltravelapp.domain.usecase.FlowUseCase
import com.example.gomeltravelapp.domain.model.Place
import com.example.gomeltravelapp.domain.repository.PlaceRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedPlacesUseCase @Inject constructor(
    private val repository: PlaceRepository,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, List<Place>>(ioDispatcher) {

    //функция выполнения данного варианта использования
    override fun execute(parameters: Unit): Flow<Resource<List<Place>>> =
        repository.getSavedPlaces()
}
