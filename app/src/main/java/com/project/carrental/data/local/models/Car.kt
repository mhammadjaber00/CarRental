package com.project.carrental.data.local.models

data class Car(
    val id: Int?,
    var name: String,
    var price: Double,
    var image: String,
    var color: String,
    var isRented: Boolean,
    var startDate: String? = "",
    var endDate: String? = ""
)
