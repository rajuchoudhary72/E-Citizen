package com.app.ecitizen.features.localization

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ecitizen.model.AppLocale
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AppLocaleViewModel @Inject constructor(
) : ViewModel() {
    val appLocaleUiState: StateFlow<AppLocaleUiState> = MutableStateFlow(
        AppLocaleUiState.Success(
            appLocale = AppLocale.values().asList(),
            selectedAppLocale = AppLocale.HINDI
        )
    )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AppLocaleUiState.Loading
        )
}

@Keep
sealed interface AppLocaleUiState {
    object Loading : AppLocaleUiState
    data class Success(
        val appLocale: List<AppLocale>,
        val selectedAppLocale: AppLocale
    ) : AppLocaleUiState


}