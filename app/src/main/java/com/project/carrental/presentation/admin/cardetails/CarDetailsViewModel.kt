package com.project.carrental.presentation.admin.cardetails

import androidx.lifecycle.ViewModel
import com.project.carrental.data.local.models.Car
import com.project.carrental.domain.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CarDetailsViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {

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