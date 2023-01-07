package com.project.carrental.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.carrental.presentation.admin.mycars.AdminCarsViewModel
import com.project.carrental.presentation.customer.availablecars.AvailableCarsViewModel

class AdminCarsViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @JvmSuppressWildcards
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdminCarsViewModel::class.java)) {
            return AdminCarsViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}