package com.example.gomeltravelapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gomeltravelapp.domain.model.Place
import com.example.gomeltravelapp.domain.model.PlaceFTS

//Класс для хранения базы данных
@Database(
    entities = [
        Place::class,
        PlaceFTS::class
    ],
    version = Constants.VERSION
)
@TypeConverters(Converters::class)
internal abstract class PlaceDataBase : RoomDatabase() {
    abstract val placesTableDao: PlacesTableDao
}
