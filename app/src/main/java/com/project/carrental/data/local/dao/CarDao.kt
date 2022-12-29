package com.project.carrental.data.local.dao

import androidx.room.*
import com.project.carrental.data.local.models.Car

@Dao
interface CarDao {

    @Query("SELECT * FROM car")
    fun getCars(): List<Car>

    @Query("SELECT * FROM car WHERE isRented = :isRentedCar")
    fun getAvailableCars(isRentedCar: Boolean = false): List<Car>

    @Insert(entity = Car::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCar(car: Car)

    @Insert(entity = Car::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCars(cars: List<Car>)

    @Update(entity = Car::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCarRented(car: Car)

    @Update(entity = Car::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCarRented(cars: List<Car>)


}