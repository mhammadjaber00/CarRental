package com.project.carrental.presentation.admin.mycars

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.carrental.data.local.models.Car
import com.project.carrental.domain.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminCarsViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState>(UIState.Nothing)
    val uiState = _uiState

    sealed class UIState {
        object Nothing : UIState()
        data class Error(val message: String) : UIState()
        data class Success(val list: List<Car>) : UIState()
        data class Loading(val isLoading: Boolean) : UIState()
    }

    fun getCars() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading(true)
            _uiState.value = UIState.Success(mainRepository.getCar())
        }
    }
}