package com.example.littlelemon.viewmodels.main

import android.content.Context
import android.content.res.Configuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.littlelemon.LittleLemonApplication
import com.example.littlelemon.data.UserPreferenceRepository
import com.example.littlelemon.model.LittleLemonUser
import com.example.littlelemon.model.MyAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale

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
            LittleLemonMainUIState(
                userWithTheme.littleLemonUser,
                isLoading = false,
                isDarkMode = userWithTheme.myAppTheme.isDarkMode,
                langCode = userWithTheme.langCode
            )
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

    fun updateLocale(context: Context, language: String) {
        context.resources.apply {
            val locale = Locale(language)
            val config = Configuration(configuration)

            context.createConfigurationContext(configuration)
            Locale.setDefault(locale)
            config.setLocale(locale)
            context.resources.updateConfiguration(config, displayMetrics)
        }
    }

    fun changeLanguage(context: Context, langCode:String){
        viewModelScope.launch {
            userPreferenceRepository.saveLanguageCode(langCode)
        }
        updateLocale(context = context, language = langCode)
    }
}