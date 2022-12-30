package com.project.carrental.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "car")
data class Car(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo var name: String,
    @ColumnInfo var price: Int,
    @ColumnInfo var image: String,
    @ColumnInfo var color: String,
    @ColumnInfo var isRented: Boolean,
    @ColumnInfo var startDate: String? = null,
    @ColumnInfo var endDate: String? = null
)
