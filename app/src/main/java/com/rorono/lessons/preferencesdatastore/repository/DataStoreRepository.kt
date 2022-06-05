package com.rorono.lessons.preferencesdatastore.repository

import android.content.Context
import android.preference.PreferenceDataStore
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.rorono.lessons.preferencesdatastore.repository.PreferenceKeys.name
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


const val PREFERENCE_NAME = "my_preference"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

private object PreferenceKeys {
    val name = stringPreferencesKey("my_name")
}

class DataStoreRepository(context: Context) {

    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun saveToDataStore(key: String) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.name] = key
        }
    }

    val readFromDataStore: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.d("TEST", exception.message.toString())
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map {  preference ->
            val name = preference[PreferenceKeys.name] ?: "none"
           name
        }

}