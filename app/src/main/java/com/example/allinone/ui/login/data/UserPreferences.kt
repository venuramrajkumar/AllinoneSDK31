package com.example.allinone.ui.login.data

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class UserPreferences @Inject constructor(application: Application) {
    private val appContext = application.applicationContext
    lateinit var  updateResult: Single<Preferences>

    val accessToken: Flow<String?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN]
        }

    val refreshToken: Flow<String?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[REFRESH_TOKEN]
        }

    suspend fun saveAccessTokens(accessToken: String,refreshToken : String) {
        appContext.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = accessToken
            preferences[REFRESH_TOKEN] = refreshToken
        }
    }


    suspend fun clear() {
        appContext.dataStore.edit { preferences ->
            preferences.clear()
        }
    }






//RXJAVA is not working as expected.
//    private val dataStore: RxDataStore<Preferences> =
//        RxPreferenceDataStoreBuilder(applicationContext, "settings").build()
//    @ExperimentalCoroutinesApi
//    fun saveAuthToken(authToken: String) {
//
//        updateResult = dataStore.updateDataAsync { prefsIn: Preferences ->
//            val mutablePreferences = prefsIn.toMutablePreferences()
//            mutablePreferences[ACCESS_TOKEN] = authToken
//            return@updateDataAsync Single.just(mutablePreferences)
//        }
//
//    }
//
//    @ExperimentalCoroutinesApi
//    val accessToken : Flowable<String>
//        get() = dataStore.data().map { preferences ->
//            preferences[ACCESS_TOKEN] ?: "Default"
//        }


    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("key_access_token")
        private val REFRESH_TOKEN = stringPreferencesKey("key_refresh_token")
    }

}