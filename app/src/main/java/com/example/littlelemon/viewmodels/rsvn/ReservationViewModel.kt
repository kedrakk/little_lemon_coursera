package com.example.littlelemon.viewmodels.rsvn

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class ReservationViewModel:ViewModel() {
    private val _uiState = MutableStateFlow(ReservationUIState())
    val uiState: StateFlow<ReservationUIState> = _uiState.asStateFlow()

    fun changePersonCount(newValue:Int){
        _uiState.update {
            it.copy(currentSelectPerson = newValue)
        }
    }

    fun onDayClicked(newDate: LocalDate){
        _uiState.update {
            it.copy(selectedDate = newDate, selectedTime = null)
        }
    }

    fun onTimeClicked(newTime:String){
        _uiState.update {
            it.copy(selectedTime = newTime)
        }
    }

    fun onReserve(){
        _uiState.update {
            it.copy(selectedDate = null, selectedTime = null, currentSelectPerson = 1)
        }
    }
}