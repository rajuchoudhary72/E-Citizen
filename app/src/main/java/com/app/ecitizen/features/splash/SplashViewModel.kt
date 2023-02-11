package com.app.ecitizen.features.splash

import androidx.annotation.Keep
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ecitizen.data.datastore.ECitizenPreferencesDataStore
import com.app.ecitizen.data.network.dto.AppFront
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
   preferencesDataStore: ECitizenPreferencesDataStore
) : ViewModel() {

    val userDto = preferencesDataStore.getUserDtoFlow()


    val uiState: StateFlow<SplashScreenUiState> =
        preferencesDataStore
            .getAppFrontFlow()
            .map {
                SplashScreenUiState.Success(appFront = it)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SplashScreenUiState.Loading
            )


}
@Keep
sealed interface SplashScreenUiState {
    object Loading : SplashScreenUiState

    data class Success(
        val appFront: AppFront?
    ) : SplashScreenUiState
}


