package com.example.littlelemoncoursera.viewmodels.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.littlelemoncoursera.LittleLemonApplication
import com.example.littlelemoncoursera.data.UserPreferenceRepository
import com.example.littlelemoncoursera.model.LittleLemonUser
import com.example.littlelemoncoursera.model.MyAppTheme
import kotlinx.coroutines.delay
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
        userPreferenceRepository.littleLemonUserWithAppTheme.map { userWithTheme ->
            delay(3000L)
            LittleLemonMainUIState(userWithTheme.littleLemonUser, isLoading = false,isDarkMode = userWithTheme.myAppTheme.isDarkMode)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(2_000),
                initialValue = LittleLemonMainUIState()
            )

    fun setUserData(littleLemonUser: LittleLemonUser) {
        viewModelScope.launch {
            userPreferenceRepository.saveLayoutPreference(littleLemonUser)
        }
    }

    fun changeTheme(newThemeData:MyAppTheme){
        viewModelScope.launch {
            userPreferenceRepository.saveThemeData(newThemeData)
        }
    }
}