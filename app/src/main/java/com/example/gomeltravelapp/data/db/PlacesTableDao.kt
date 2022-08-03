package com.example.gomeltravelapp.data.db

import androidx.room.Dao
import androidx.room.Query
import com.example.gomeltravelapp.domain.model.Place
import com.example.gomeltravelapp.domain.model.PlaceFTS
import com.example.gomeltravelapp.domain.model.Point
import kotlinx.coroutines.flow.Flow

//DAO, предоставляющий методы для работы с бд
@Dao
interface PlacesTableDao {

    @Query("SELECT * FROM ${Place.Schema.TABLE_NAME}")
    fun getPlaces(): Flow<List<Place>>

    @Query(
        """
        SELECT * FROM ${Place.Schema.TABLE_NAME} 
        WHERE ${Place.Schema.POINT} LIKE :point
        """
    )
    fun getPlacesOnMap(point: Point): Flow<List<Place>>

    @Query(
        """
        SELECT * FROM ${Place.Schema.TABLE_NAME} 
        WHERE ${Place.Schema.TYPES} LIKE :type
        """
    )
    fun getPlacesByType(type: String): Flow<List<Place>>

    /*@Query(
        """
        SELECT * FROM ${Place.Schema.TABLE_NAME} 
        WHERE ${Place.Schema.POINT} LIKE :point
        """
    )
    fun getPlacesOnMap(point: Point): Flow<List<Place>>*/

    /*@Query(
        """
        SELECT * FROM ${Place.Schema.TABLE_NAME}
        JOIN ${PlaceFTS.Schema.TABLE_NAME_FTS}
        ON ${Place.Schema.TABLE_NAME}.name = ${PlaceFTS.Schema.TABLE_NAME_FTS}.name 
        AND ${Place.Schema.TABLE_NAME}.types = ${PlaceFTS.Schema.TABLE_NAME_FTS}.types
        WHERE ${PlaceFTS.Schema.TABLE_NAME_FTS} MATCH :query AND ${PlaceFTS.Schema.TABLE_NAME_FTS} MATCH :type
        """
    )
    fun searchPlaces(type: String, query: String): Flow<List<Place>>*/

    @Query(
        """
        SELECT * FROM ${Place.Schema.TABLE_NAME} 
        WHERE ${Place.Schema.ID} = :id
        """
    )
    suspend fun getPlaceById(id: Int?): Place

    @Query(
        """
        UPDATE ${Place.Schema.TABLE_NAME} 
            SET ${Place.Schema.SAVED} = 1
        WHERE ${Place.Schema.ID} = :id
        """
    )
    suspend fun savePlace(id: Int?)

    @Query(
        """
        UPDATE ${Place.Schema.TABLE_NAME} 
            SET ${Place.Schema.SAVED} = 0
        WHERE ${Place.Schema.ID} = :id
        """
    )
    suspend fun deleteSavedPlace(id: Int?)

    @Query(
        """
        SELECT * FROM ${Place.Schema.TABLE_NAME} 
        WHERE ${Place.Schema.SAVED} = 1
        """
    )
    fun getSavedPlaces(): Flow<List<Place>>

    @Query(
        """
        SELECT COUNT(*) FROM ${Place.Schema.TABLE_NAME} 
        WHERE ${Place.Schema.ID} = :id AND ${Place.Schema.SAVED} = 1
        """
    )
    suspend fun isPlaceSaved(id: Int?): Boolean

    /*@Query(
        """
        SELECT * FROM ${Place.Schema.TABLE_NAME}
        JOIN ${PlaceFTS.Schema.TABLE_NAME_FTS}
        ON ${Place.Schema.TABLE_NAME}.name = ${PlaceFTS.Schema.TABLE_NAME_FTS}.name
        WHERE ${PlaceFTS.Schema.TABLE_NAME_FTS} MATCH :query
        """
    )
    fun searchPlaces(query: String): Flow<List<Place>>*/

    @Query(
        """
            SELECT * FROM ${Place.Schema.TABLE_NAME} 
            JOIN ${PlaceFTS.Schema.TABLE_NAME_FTS} 
            ON ${Place.Schema.TABLE_NAME}.id == ${PlaceFTS.Schema.TABLE_NAME_FTS}.id 
            WHERE ${PlaceFTS.Schema.TABLE_NAME_FTS}.name MATCH :query GROUP BY ${Place.Schema.TABLE_NAME}.id
        """
    )
    fun searchPlaces(query: String): Flow<List<Place>>

}