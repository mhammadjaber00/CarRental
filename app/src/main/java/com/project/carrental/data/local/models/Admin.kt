package com.project.carrental.data.local.models

data class Admin(
    val id: Int,
    var name: String,
    var email: String,
    var password: String,
    var phone: String,
    var listOfCars: List<Car>
)
