package com.example.gomeltravelapp.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

abstract class SuspendFlowUseCase<in Params, out Resource>(
    private val coroutineDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(parameters: Params): Flow<Resource> {
        return withContext(coroutineDispatcher) {
            execute(parameters)
                .flowOn(coroutineDispatcher)
        }
    }

    abstract suspend fun execute(parameters: Params): Flow<Resource>
}