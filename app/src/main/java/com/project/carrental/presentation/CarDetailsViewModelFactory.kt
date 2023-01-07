package com.project.carrental.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.carrental.presentation.admin.cardetails.CarDetailsViewModel
import com.project.carrental.presentation.customer.availablecars.AvailableCarsViewModel

class CarDetailsViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @JvmSuppressWildcards
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarDetailsViewModel::class.java)) {
            return CarDetailsViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}