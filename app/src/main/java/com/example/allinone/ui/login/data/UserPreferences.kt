package com.example.allinone.ui.login.data

import android.app.Application
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder
import androidx.datastore.rxjava3.RxDataStore
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


class UserPreferences @Inject constructor(application: Application) {
    private val applicationContext = application.applicationContext

    //    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val dataStore: RxDataStore<Preferences> =
        RxPreferenceDataStoreBuilder(applicationContext, "settings").build()

    @ExperimentalCoroutinesApi
    fun saveAuthToken(authToken: String) {

        val updateResult: Single<Preferences> = dataStore.updateDataAsync { prefsIn: Preferences ->
            val mutablePreferences = prefsIn.toMutablePreferences()
            mutablePreferences[ACCESS_TOKEN] = authToken
            return@updateDataAsync Single.just(mutablePreferences)
        }

    }

    @ExperimentalCoroutinesApi
    val accessToken : Flowable<String>
        get() = dataStore.data().map { preferences ->
            preferences[ACCESS_TOKEN]?:"Default"
        }

    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("key_access_token")
        private val REFRESH_TOKEN = stringPreferencesKey("key_refresh_token")
    }

}