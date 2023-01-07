package com.project.carrental.presentation.customer.mycars

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.carrental.data.local.models.Car
import com.project.carrental.domain.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MyCarsViewModel(context: Context) :
    ViewModel() {

    private val mainRepository = MainRepository(context)

    sealed class UIEvent {
        object Nothing : UIEvent()
        object Loading : UIEvent()
        data class Success(val carList: List<Car>) : UIEvent()
        data class Error(val message: String) : UIEvent()
    }

    private val _availableCarsState =
        MutableStateFlow<UIEvent>(UIEvent.Nothing)

    val availableCars = _availableCarsState

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
}