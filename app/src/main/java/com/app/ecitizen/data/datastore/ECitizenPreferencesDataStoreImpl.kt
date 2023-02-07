package com.app.ecitizen.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.app.ecitizen.data.network.dto.UserDto
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ECitizenPreferencesDataStoreImpl @Inject constructor(
    @ApplicationContext val context: Context,
    private val gson: Gson
) : ECitizenPreferencesDataStore {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    override suspend fun getAuthToken(): String? {
        return getUserDtoFlow().first()?.apiAccessToken
    }

    override suspend fun saveUserData(userDto: UserDto) {
        context.dataStore.edit { userSession ->
            userSession[USER_DATA] = gson.toJson(userDto)
        }
    }

    override fun getUserDtoFlow(): Flow<UserDto?> {
        return context.dataStore.data.map { userSession ->
            val userDtoGson = userSession[USER_DATA]
            if (userDtoGson.isNullOrBlank()) {
                null
            } else
                gson.fromJson(userDtoGson, UserDto::class.java)
        }
    }

    companion object {
        private val USER_DATA = stringPreferencesKey("user_data")
    }
}