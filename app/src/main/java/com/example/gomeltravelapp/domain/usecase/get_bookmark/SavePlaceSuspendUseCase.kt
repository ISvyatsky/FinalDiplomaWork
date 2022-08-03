package com.example.gomeltravelapp.domain.usecase.get_bookmark

import com.example.gomeltravelapp.di.IoDispatcher
import com.example.gomeltravelapp.domain.usecase.SuspendUseCase
import com.example.gomeltravelapp.domain.repository.PlaceRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SavePlaceSuspendUseCase @Inject constructor(
    private val repository: PlaceRepository,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
) : SuspendUseCase<Int, Unit>(ioDispatcher) {

    //функция выполнения данного варианта использования
    override suspend fun execute(
        parameters: Int
    ) = repository.savePlace(parameters)
}
