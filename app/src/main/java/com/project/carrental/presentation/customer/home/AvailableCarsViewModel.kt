package com.project.carrental.presentation.customer.home

import androidx.lifecycle.ViewModel
import com.project.carrental.data.local.models.Car
import com.project.carrental.domain.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class AvailableCarsViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {
    val carList: List<Car>
        get() {
            return runBlocking {
                mainRepository.getCar()
            }
        }
}