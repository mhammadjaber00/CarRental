package com.project.carrental.presentation.customer.mycars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.carrental.data.local.models.Car
import com.project.carrental.domain.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyCarsViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {

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
                _availableCarsState.value = UIEvent.Success(mainRepository.getCar())

            } catch (e: Exception) {
                _availableCarsState.value = UIEvent.Error(e.message ?: "Error")
            }
        }
    }
}