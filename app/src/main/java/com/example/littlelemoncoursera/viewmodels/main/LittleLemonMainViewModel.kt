package com.example.littlelemoncoursera.viewmodels.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.littlelemoncoursera.LittleLemonApplication
import com.example.littlelemoncoursera.data.UserPreferenceRepository
import com.example.littlelemoncoursera.model.LittleLemonUser
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LittleLemonMainViewModel(private val userPreferenceRepository: UserPreferenceRepository) :
    ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as LittleLemonApplication)
                LittleLemonMainViewModel(application.userPreferenceRepository)
            }
        }
    }

    val uiState: StateFlow<LittleLemonMainUIState> =
        userPreferenceRepository.littleLemonUser.map { user ->
            LittleLemonMainUIState(user)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = LittleLemonMainUIState()
            )

    fun setUserData(littleLemonUser: LittleLemonUser) {
        viewModelScope.launch {
            userPreferenceRepository.saveLayoutPreference(littleLemonUser)
        }
    }
}