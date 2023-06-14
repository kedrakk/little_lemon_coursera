package com.example.littlelemon.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.littlelemon.model.LittleLemonUser
import com.example.littlelemon.model.LittleLemonUserWithAppTheme
import com.example.littlelemon.model.MyAppTheme
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
        val ISDARKMODE = booleanPreferencesKey("is_dark_mode")
        val LANGCODE = stringPreferencesKey("lang_code")
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

    suspend fun saveThemeData(appTheme: MyAppTheme){
        dataStore.edit {prefs->
            prefs[ISDARKMODE] = appTheme.isDarkMode
        }
    }

    suspend fun saveLanguageCode(langCode:String){
        dataStore.edit {prefs->
            prefs[LANGCODE] = langCode
        }
    }

    val littleLemonUserWithAppTheme:Flow<LittleLemonUserWithAppTheme> = dataStore.data.catch {
        if(it is IOException) {
            Log.e(TAG, "Error reading preferences.", it)
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map {
        LittleLemonUserWithAppTheme(
            littleLemonUser = LittleLemonUser(
                firstName = it[FIRST_NAME] ?: "",
                lastName = it[LAST_NAME] ?: "",
                email = it[EMAIL] ?: "",
                password = it[PASSWORD] ?: ""
            ),
            myAppTheme = MyAppTheme(isDarkMode = it[ISDARKMODE]?:false),
            langCode = it[LANGCODE]?:"en"
        )
    }

//    val littleLemonUser:Flow<LittleLemonUser> = dataStore.data.catch {
//        if(it is IOException) {
//            Log.e(TAG, "Error reading preferences.", it)
//            emit(emptyPreferences())
//        } else {
//            throw it
//        }
//    }.map {
//        LittleLemonUser(
//            firstName = it[FIRST_NAME] ?: "",
//            lastName = it[LAST_NAME] ?: "",
//            email = it[EMAIL] ?: "",
//            password = it[PASSWORD] ?: ""
//        )
//    }
//
//    val myAppTheme:Flow<MyAppTheme> = dataStore.data.catch {
//        if(it is IOException) {
//            Log.e(TAG, "Error reading preferences.", it)
//            emit(emptyPreferences())
//        } else {
//            throw it
//        }
//    }.map {
//        MyAppTheme(
//            isDarkMode = it[ISDARKMODE]?:false
//        )
//    }
}