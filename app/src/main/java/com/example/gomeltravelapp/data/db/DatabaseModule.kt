package com.example.gomeltravelapp.data.db

import android.content.Context
import androidx.room.Room
import com.example.gomeltravelapp.data.repository.LocationRepositoryImpl
import com.example.gomeltravelapp.data.repository.PlaceRepositoryImpl
import com.example.gomeltravelapp.data.remote.LocationRemoteSource
import com.example.gomeltravelapp.domain.repository.LocationRepository
import com.example.gomeltravelapp.domain.repository.PlaceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

//Модуль, в котором происходит инициализация бд и репозиториев.
@Module
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun providePlaceDatabase(@ApplicationContext context: Context): PlaceDataBase {
        return Room.databaseBuilder(
            context,
            PlaceDataBase::class.java,
            Constants.DATABASE_NAME
        ).createFromAsset("database/gomelplaces.db")
            /*.addCallback(object : RoomDatabase.Callback() { // Add this callback
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    db.execSQL("INSERT INTO ${PlaceFTS.Schema.TABLE_NAME_FTS}(${PlaceFTS.Schema.TABLE_NAME_FTS}) VALUES ('rebuild')")
                }
            })
            .fallbackToDestructiveMigration()*/
            .addMigrations(*MIGRATIONS)
            .build()
    }

    @Provides
    @Singleton
    fun providePlacesTable(placeDataBase: PlaceDataBase): PlacesTableDao {
        return placeDataBase.placesTableDao
    }

    @Provides
    @Singleton
    fun providePlaceRepository(placeDataBase: PlaceDataBase): PlaceRepository {
        return PlaceRepositoryImpl(placeDataBase.placesTableDao)
    }

    @OptIn(InternalCoroutinesApi::class, ExperimentalCoroutinesApi::class)
    @Provides
    fun provideLocationRepository(@ApplicationContext context: Context, placeDataBase: PlaceDataBase): LocationRepository {
        return LocationRepositoryImpl(
                    placeDataBase.placesTableDao,
                    LocationRemoteSource(
                        context
                    )
        )
    }
}

