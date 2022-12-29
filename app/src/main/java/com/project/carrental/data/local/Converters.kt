package com.project.carrental.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.project.carrental.data.local.models.Car

class Converters {

    @TypeConverter
    fun fromCarList(value: List<Car>): String {
        val gson = Gson()
        val type = object : com.google.gson.reflect.TypeToken<List<Car>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCarList(value: String): List<Car> {
        val gson = Gson()
        val type = object : com.google.gson.reflect.TypeToken<List<Car>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        val gson = Gson()
        val type = object : com.google.gson.reflect.TypeToken<List<String>>() {}.type
        return gson.toJson(value, type)
    }
}