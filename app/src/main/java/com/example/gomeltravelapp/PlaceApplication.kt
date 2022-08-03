package com.example.gomeltravelapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//Класс приложения, который предоставляет DI инфу
@HiltAndroidApp
class PlaceApplication: Application()