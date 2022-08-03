package com.example.gomeltravelapp.domain.usecase

import com.example.gomeltravelapp.common.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

/*
 * Выполняет бизнес-логику в своем методе execute и продолжает публиковать обновления результата
 * по мере [Resource<R>].
 * Обработка исключения (выдача [Resource.Error] в результат) является обязанностью подклассов.
 */
abstract class FlowUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    operator fun invoke(parameters: P): Flow<Resource<R>> = execute(parameters)
        .catch { e -> emit(Resource.Error(Exception(e))) }
        .flowOn(coroutineDispatcher)

    protected abstract fun execute(parameters: P): Flow<Resource<R>>
}
