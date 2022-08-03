package com.example.gomeltravelapp.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.Index
import kotlinx.parcelize.Parcelize

//Сущность достопримечательности , он же и entity-объект для хранения данных в бд
@Parcelize
@Entity(
    tableName = Place.Schema.TABLE_NAME,
    primaryKeys = [Place.Schema.ID]
)
data class Place(
    @ColumnInfo(name = Schema.DESTINATION)
    val dist: Double,
    @ColumnInfo(name = Schema.TYPES)
    val types: List<String>,
    @ColumnInfo(name = Schema.NAME)
    val name: String,
    @ColumnInfo(name = Schema.IMAGE)
    val image: List<String>,
    /*@ColumnInfo(name = Schema.IMAGE)
    val image: String,*/
    @ColumnInfo(name = Schema.POINT)
    val point: Point,
    @ColumnInfo(name = Schema.RATE)
    val rate: Int,
    @ColumnInfo(name = Schema.SAVED)
    var saved: Boolean = false,
    @ColumnInfo(name = Schema.ID)
    val id: Int,
    @ColumnInfo(name = Schema.ADRESS)
    val adress: String,
    @ColumnInfo(name = Schema.INFORMATION)
    val information: String

): Parcelable {
    object Schema {
        const val TABLE_NAME = "gomelplaces"
        const val DESTINATION = "dist"
        const val TYPES = "types"
        const val NAME = "name"
        const val ADRESS = "adress"
        const val IMAGE = "image"
        const val INFORMATION = "information"
        const val POINT = "point"
        const val RATE = "rate"
        const val ID = "id"
        const val SAVED = "saved"
    }
}

@Entity(
    tableName = PlaceFTS.Schema.TABLE_NAME_FTS
)
@Fts4(contentEntity = Place::class)
data class PlaceFTS(
    val id: Int,
    @ColumnInfo(name = Schema.TYPES_FTS)
    val types: List<String>,
    @ColumnInfo(name = Schema.NAME_FTS)
    val name: String,
) {
    object Schema {
        const val TABLE_NAME_FTS = "gomelplacesfts"
        const val TYPES_FTS = "types"
        const val NAME_FTS = "name"
    }
}