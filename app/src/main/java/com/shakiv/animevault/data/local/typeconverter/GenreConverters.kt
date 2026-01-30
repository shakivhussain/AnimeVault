package com.shakiv.animevault.data.local.typeconverter


import androidx.room.TypeConverter

class GenreConverters {
    @TypeConverter
    fun fromString(value: String?): List<String> {
        // Turns "Action,Drama,Sci-Fi" back into a List
        return value?.split(",")?.map { it.trim() } ?: emptyList()
    }

    @TypeConverter
    fun fromList(list: List<String>?): String {
        // Turns the List into "Action,Drama,Sci-Fi" to save in DB
        return list?.joinToString(separator = ",") ?: ""
    }
}