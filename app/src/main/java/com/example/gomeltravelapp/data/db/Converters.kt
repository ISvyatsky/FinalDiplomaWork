package com.example.gomeltravelapp.data.db

import androidx.room.TypeConverter
import com.example.gomeltravelapp.domain.model.Point
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

//Конвентер, преобразовывающий для бд сложные типы в примитивные
class Converters {
    @TypeConverter
    fun fromDetails(countryLang: Point?): String? {
        val type = object : TypeToken<Point>() {}. type
        return Gson().toJson(countryLang, type)
    }
    @TypeConverter
    fun toDetails(countryLangString: String?): Point? {
        val type = object : TypeToken<Point>() {}. type
        return Gson().fromJson<Point>(countryLangString, type)
    }

    @TypeConverter
    fun fromTypes(str: List<String>): String {
        val pictures = StringBuilder()
        for (s in str) pictures.append(s).append(",")
        return pictures.toString()
    }

    @TypeConverter
    fun toTypes(str: String): List<String?> {
        val myStrings: ArrayList<String> = ArrayList()
            for(s in str.split(","))
                    myStrings.add(s)
        return myStrings
    }
}