package com.project.carrental.domain

import android.content.ContentValues
import android.content.Context
import com.google.gson.Gson
import com.project.carrental.data.local.DatabaseHelper
import com.project.carrental.data.local.models.Admin
import com.project.carrental.data.local.models.Car
import com.project.carrental.data.local.models.Customer


class MainRepository(context: Context) {

    private val databaseHelper = DatabaseHelper(context)

    fun addAdmin(admin: Admin) {
        val database = databaseHelper.writableDatabase

        val contentValues = ContentValues()
        contentValues.put("name", admin.name)
        contentValues.put("email", admin.email)
        contentValues.put("password", admin.password)
        contentValues.put("phone", admin.phone)
        val gson = Gson()
        val carsJson = gson.toJson(admin.listOfCars)
        contentValues.put("cars", carsJson)

        database.insert("Admin", null, contentValues)
        database.close()
    }

    fun getAdmin(id: Int): Admin {
        val database = databaseHelper.readableDatabase

        val cursor = database.query(
            "Admin",
            arrayOf("id", "name", "email", "password", "phone", "cars"),
            "id = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        cursor.moveToFirst()
        val adminId = cursor.getInt(0)
        val name = cursor.getString(1)
        val email = cursor.getString(2)
        val password = cursor.getString(3)
        val phone = cursor.getString(4)
        val carsJson = cursor.getString(5)
        val gson = Gson()
        val cars = gson.fromJson(carsJson, Array<Car>::class.java).toList()

        cursor.close()
        database.close()

        return Admin(adminId, name, email, password, phone, cars)
    }

    private fun addCustomer(customer: Customer) {
        val database = databaseHelper.writableDatabase

        val contentValues = ContentValues()
        contentValues.put("name", customer.name)
        contentValues.put("email", customer.email)
        contentValues.put("password", customer.password)
        contentValues.put("phone", customer.phone)
        val gson = Gson()
        val carsJson = gson.toJson(customer.listOfRentedCars)
        contentValues.put("cars", carsJson)

        database.insert("Customer", null, contentValues)
        database.close()
    }

    fun getCustomer(id: Int): Customer {
        val database = databaseHelper.readableDatabase

        val cursor = database.query(
            "Customer",
            arrayOf("id", "name", "email", "password", "phone", "cars"),
            "id = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        cursor.moveToFirst()
        val customerId = cursor.getInt(0)
        val name = cursor.getString(1)
        val email = cursor.getString(2)
        val password = cursor.getString(3)
        val phone = cursor.getString(4)
        val carsJson = cursor.getString(5)
        val gson = Gson()
        val cars = gson.fromJson(carsJson, Array<Car>::class.java).toList()

        cursor.close()
        database.close()

        return Customer(customerId, name, email, password, phone, cars)
    }


    fun insertCar(car: Car) {
        val database = databaseHelper.writableDatabase

        val contentValues = ContentValues()
        contentValues.put("name", car.name)
        contentValues.put("price", car.price)
        contentValues.put("image", car.image)
        contentValues.put("color", car.color)
        contentValues.put("isRented", car.isRented)
        contentValues.put("startDate", car.startDate)
        contentValues.put("endDate", car.endDate)

        database.insert("Car", null, contentValues)
        database.close()
    }

    fun updateCar(car: Car) {
        val database = databaseHelper.writableDatabase

        val contentValues = ContentValues()
        contentValues.put("name", car.name)
        contentValues.put("price", car.price)
        contentValues.put("image", car.image)
        contentValues.put("color", car.color)
        contentValues.put("isRented", car.isRented)
        contentValues.put("startDate", car.startDate)
        contentValues.put("endDate", car.endDate)

        val selection = "id = ?"
        val selectionArgs = arrayOf(car.id.toString())
        database.update("Car", contentValues, selection, selectionArgs)
        database.close()
    }

    fun getAllCars(): List<Car> {
        val database = databaseHelper.readableDatabase

        val cursor = database.query(
            "Car",
            arrayOf("id", "name", "price", "image","color", "isRented", "startDate", "endDate"),
            null,
            null,
            null,
            null,
            null
        )

        val cars = mutableListOf<Car>()
        while (cursor.moveToNext()) {
            val carId = cursor.getInt(0)
            val name = cursor.getString(1)
            val price = cursor.getDouble(2)
            val image = cursor.getString(3)
            val color = cursor.getString(4)
            val isRented = cursor.getInt(5) == 1
            val startDate = cursor.getString(6)
            val endDate = cursor.getString(7)

            cars.add(Car(carId, name, price, image, color, isRented, startDate, endDate))
        }
        cursor.close()
        database.close()

        return cars
    }

}