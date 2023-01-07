package com.project.carrental.presentation.admin.cardetails

import android.content.Context
import androidx.lifecycle.ViewModel
import com.project.carrental.data.local.models.Car
import com.project.carrental.domain.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow

class CarDetailsViewModel(context: Context) :
    ViewModel() {

    private val mainRepository = MainRepository(context)

    sealed class UIEvent {
        object Nothing : UIEvent()
        object Loading : UIEvent()
        data class Success(val car: Car) : UIEvent()
        data class Error(val message: String) : UIEvent()
    }

    private val _carDetailsState =
        MutableStateFlow<UIEvent>(UIEvent.Nothing)

    val carDetailsState = _carDetailsState

    suspend fun updateCar(car: Car) {
        _carDetailsState.value = UIEvent.Loading
        try {
            mainRepository.insertCar(car)
            _carDetailsState.value = UIEvent.Success(car)
        } catch (e: Exception) {
            _carDetailsState.value = UIEvent.Error(e.message ?: "Error")
        }
    }


}