package com.project.carrental.presentation.customer.availablecars

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.carrental.data.local.models.Car
import com.project.carrental.domain.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AvailableCarsViewModel(context: Context) :
    ViewModel() {

    private val mainRepository = MainRepository(context)

    sealed class UIEvent {
        object Nothing : UIEvent()
        object Loading : UIEvent()
        data class Success(val carList: List<Car>) : UIEvent()
        data class Edit(val car: Car) : UIEvent()
        data class Error(val message: String) : UIEvent()
    }

    private val _availableCarsState =
        MutableStateFlow<UIEvent>(UIEvent.Nothing)

    val availableCars = _availableCarsState

    private val _updateCarState =
        MutableStateFlow<UIEvent>(UIEvent.Nothing)

    suspend fun fetchCars() {
        viewModelScope.launch(Dispatchers.IO) {
            _availableCarsState.value = UIEvent.Loading
            try {
                _availableCarsState.value = UIEvent.Success(mainRepository.getAllCars())

            } catch (e: Exception) {
                _availableCarsState.value = UIEvent.Error(e.message ?: "Error")
            }
        }
    }

    suspend fun updateCar(car: Car, startDate: String, endDate: String) {
        _updateCarState.value = UIEvent.Loading
        try {
            mainRepository.updateCar(
                Car(
                    car.id,
                    car.name,
                    car.price,
                    car.image,
                    car.color,
                    isRented = true,
                    startDate = startDate,
                    endDate = endDate
                )
            )
            _updateCarState.value = UIEvent.Edit(car)
        } catch (e: Exception) {
            _updateCarState.value = UIEvent.Error(e.message ?: "Error")
        }
    }

}