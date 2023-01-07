package com.project.carrental.data.local.models

data class Customer(
    val id: Int,
    var name: String,
    var email: String,
    var password: String,
    var phone: String,
    var listOfRentedCars: List<Car>
)

