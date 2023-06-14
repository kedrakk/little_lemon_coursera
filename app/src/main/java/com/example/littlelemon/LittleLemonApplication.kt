package com.example.littlelemon

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.littlelemon.data.UserPreferenceRepository
import com.example.littlelemon.model.CartItem

//user preference configs
private const val USER_PREFERENCE_NAME = "user_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = USER_PREFERENCE_NAME
)

var cartItems:MutableList<CartItem> = mutableListOf()

class LittleLemonApplication:Application() {
    lateinit var userPreferenceRepository: UserPreferenceRepository

    override fun onCreate() {
        super.onCreate()
        userPreferenceRepository = UserPreferenceRepository(dataStore)
    }
}