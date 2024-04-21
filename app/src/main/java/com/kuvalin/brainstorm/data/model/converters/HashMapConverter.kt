package com.kuvalin.brainstorm.data.model.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class HashMapConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromHashMap(value: HashMap<String, List<String>>?): String? {
        return if (value == null) {
            null
        } else {
            gson.toJson(value)
        }
    }

    @TypeConverter
    fun toHashMap(value: String?): HashMap<String, List<String>>? {
        return if (value.isNullOrEmpty()) {
            null
        } else {
            val type = object : TypeToken<HashMap<String, List<String>>>() {}.type
            gson.fromJson(value, type)
        }
    }
}