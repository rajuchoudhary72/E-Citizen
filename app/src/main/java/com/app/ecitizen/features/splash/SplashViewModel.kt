package com.app.ecitizen.features.splash

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ecitizen.data.datastore.ECitizenPreferencesDataStore
import com.app.ecitizen.data.network.dto.UserDto
import com.app.ecitizen.features.localization.AppLocaleUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val preferencesDataStore: ECitizenPreferencesDataStore
) : ViewModel(){

    val uiState: Flow<SplashScreenUiState> =
        preferencesDataStore
            .getUserDtoFlow()
            .map {
                SplashScreenUiState.Success(userDto = it)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SplashScreenUiState.Loading
            )


}

sealed interface SplashScreenUiState{
    object Loading:SplashScreenUiState

    data class Success(
        val userDto: UserDto?
    ):SplashScreenUiState
}


