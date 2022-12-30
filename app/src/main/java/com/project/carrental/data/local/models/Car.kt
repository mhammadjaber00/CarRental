package com.project.carrental.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "car")
data class Car(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo var name: String,
    @ColumnInfo var price: Double,
    @ColumnInfo var image: String,
    @ColumnInfo var color: String,
    @ColumnInfo var isRented: Boolean,
    @ColumnInfo var startDate: String? = "",
    @ColumnInfo var endDate: String? = ""
)
