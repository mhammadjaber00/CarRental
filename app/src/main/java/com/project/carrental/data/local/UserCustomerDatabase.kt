package com.project.carrental.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.carrental.data.local.dao.AdminDao
import com.project.carrental.data.local.dao.CarDao
import com.project.carrental.data.local.dao.CustomerDao
import com.project.carrental.data.local.models.Admin
import com.project.carrental.data.local.models.Car
import com.project.carrental.data.local.models.Customer

@Database(
    entities = [Admin::class, Customer::class, Car::class],
    version = 1,
    exportSchema = true,
    autoMigrations = []
)

@TypeConverters(Converters::class)
abstract class UserCustomerDatabase : RoomDatabase() {
    abstract fun adminDao(): AdminDao
    abstract fun customerDao(): CustomerDao
    abstract fun carDao(): CarDao
}

