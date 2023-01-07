package com.project.carrental.presentation.admin.mycars

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.carrental.data.local.models.Car
import com.project.carrental.domain.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AdminCarsViewModel(
    context: Context
) : ViewModel() {

    private val mainRepository = MainRepository(context)

    private val _uiState = MutableStateFlow<UIState>(UIState.Nothing)
    val uiState = _uiState

    sealed class UIState {
        object Nothing : UIState()
        data class Error(val message: String) : UIState()
        data class Success(val list: List<Car>) : UIState()
        data class Loading(val isLoading: Boolean) : UIState()
    }

    fun getCars() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = UIState.Loading(true)
            _uiState.value = UIState.Success(mainRepository.getAllCars())
        }
    }
}