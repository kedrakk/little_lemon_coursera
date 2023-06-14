package com.example.littlelemon.viewmodels.onboarding

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OnboardingViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(OnboardingUIState())
    val uiState: StateFlow<OnboardingUIState> = _uiState.asStateFlow()

    fun changeIndex(newIndex: Int) {
        _uiState.update {
            it.copy(currentStep = newIndex)
        }
    }

    fun changePasswordVisibility() {
        _uiState.update {
            it.copy(obscured = !_uiState.value.obscured)
        }
    }
}