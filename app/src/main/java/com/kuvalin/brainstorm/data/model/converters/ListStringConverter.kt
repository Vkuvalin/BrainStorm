package com.kuvalin.brainstorm.data.model.converters

import androidx.room.TypeConverter

class ListStringConverter {
    @TypeConverter
    fun fromListString(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toListString(data: String): List<String> {
        return data.split(",").map { it.trim() }
    }
}