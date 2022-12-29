package com.project.carrental.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customer")
data class Customer(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val name: String,
    @ColumnInfo val email: String,
    @ColumnInfo val password: String,
    @ColumnInfo val phone: String,
    @ColumnInfo val listOfRentedCars: List<Car>
)

