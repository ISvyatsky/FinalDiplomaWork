package com.example.gomeltravelapp.domain.usecase.get_detail_place

import com.example.gomeltravelapp.di.IoDispatcher
import com.example.gomeltravelapp.domain.usecase.SuspendUseCase
import com.example.gomeltravelapp.domain.model.Place
import com.example.gomeltravelapp.domain.repository.PlaceRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetPlaceDetailUseCase @Inject constructor(
    private val repository: PlaceRepository,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
) : SuspendUseCase<Int, Place>(ioDispatcher) {

    //функция выполнения данного варианта использования
    override suspend fun execute(parameters: Int): Place =
        repository.getPlaceById(parameters)
}



