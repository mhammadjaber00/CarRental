package com.project.carrental.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "car")
data class Car(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val name: String,
    @ColumnInfo val price: Int,
    @ColumnInfo val image: String,
    @ColumnInfo val color: String,
    @ColumnInfo val isRented : Boolean,

)
