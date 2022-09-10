package com.codinginflow.watchmovie.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class IntegerListTypeConverter {

    @TypeConverter
    fun stringToIntegerList(data: String?): MutableList<Int> {

        val gson = Gson()

        if (data == null || data.isEmpty() || data.equals("null")) {
            return mutableListOf<Int>()
        }

        val listType = object : TypeToken<List<Int>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun integerListToString(someObjects: List<Int>?): String {
        val gson = Gson()

        return gson.toJson(someObjects)
    }

}