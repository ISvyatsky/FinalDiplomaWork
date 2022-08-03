package com.example.gomeltravelapp.common

//Класс-оболочка, содержащий инфу о фактических данных для состояния юзер-интерфейса
sealed class Resource<out R> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val exception: Exception) : Resource<Nothing>()
}

val <T> Resource<T>.data: T?
    get() = (this as? Resource.Success)?.data
