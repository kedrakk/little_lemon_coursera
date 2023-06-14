package com.example.littlelemon.viewmodels.rsvn

import java.time.LocalDate

data class ReservationUIState(val currentSelectPerson: Int = 1, val selectedDate: LocalDate?=null,val selectedTime:String? = null)