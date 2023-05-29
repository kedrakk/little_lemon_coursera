package com.example.littlelemoncoursera.viewmodels.rsvn

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ReservationViewModel:ViewModel() {
    private val _uiState = MutableStateFlow(ReservationUIState())
    val uiState: StateFlow<ReservationUIState> = _uiState.asStateFlow()

    fun changePersonCount(newValue:Int){
        _uiState.update {
            it.copy(currentSelectPerson = newValue)
        }
    }
}