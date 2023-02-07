package com.app.ecitizen.data.datastore

import com.app.ecitizen.data.network.dto.UserDto
import kotlinx.coroutines.flow.Flow

interface ECitizenPreferencesDataStore {
    suspend fun getAuthToken(): String?

    suspend fun saveUserData(userDto: UserDto)

    fun getUserDtoFlow(): Flow<UserDto?>


}