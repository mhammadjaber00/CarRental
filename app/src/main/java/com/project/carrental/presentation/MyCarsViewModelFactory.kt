package com.project.carrental.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.carrental.presentation.customer.availablecars.AvailableCarsViewModel
import com.project.carrental.presentation.customer.mycars.MyCarsViewModel

class MyCarsViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @JvmSuppressWildcards
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyCarsViewModel::class.java)) {
            return MyCarsViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}