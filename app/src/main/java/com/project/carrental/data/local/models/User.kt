package com.project.carrental.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val name: String,
    @ColumnInfo val email: String,
    @ColumnInfo val password: String,
    @ColumnInfo val phone: String,
    @ColumnInfo val listOfCars: List<Car>
)
