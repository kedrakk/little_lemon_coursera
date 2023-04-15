package com.example.littlelemoncoursera.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.littlelemoncoursera.model.LittleLemonUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferenceRepository(private val dataStore: DataStore<Preferences>) {
    private companion object {
        val FIRST_NAME = stringPreferencesKey("first_name")
        val LAST_NAME = stringPreferencesKey("last_name")
        val EMAIL = stringPreferencesKey("email")
        val PASSWORD = stringPreferencesKey("password")
        const val TAG = "UserPreferencesRepo"
    }

    suspend fun saveLayoutPreference(littleLemonUser: LittleLemonUser) {
        dataStore.edit { prefs ->
            prefs[FIRST_NAME] = littleLemonUser.firstName
            prefs[LAST_NAME] = littleLemonUser.lastName
            prefs[EMAIL] = littleLemonUser.email
            prefs[PASSWORD] = littleLemonUser.password
        }
    }

    val littleLemonUser:Flow<LittleLemonUser> = dataStore.data.catch {
        if(it is IOException) {
            Log.e(TAG, "Error reading preferences.", it)
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map {
        LittleLemonUser(
            firstName = it[FIRST_NAME] ?: "",
            lastName = it[LAST_NAME] ?: "",
            email = it[EMAIL] ?: "",
            password = it[PASSWORD] ?: ""
        )
    }
}