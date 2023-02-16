package com.app.ecitizen.features.localization

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ecitizen.data.datastore.ECitizenPreferencesDataStore
import com.app.ecitizen.model.AppLocale
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppLocaleViewModel @Inject constructor(
   private val preferencesDataStore: ECitizenPreferencesDataStore
) : ViewModel() {

   private val _changedLocale = MutableStateFlow<AppLocale?>(null)
    val changedLocale = _changedLocale

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


    fun updateAppLocale(locale: AppLocale){
        viewModelScope.launch {
            if(preferencesDataStore.getAppLocale() != locale){
                preferencesDataStore.setAppLocale(locale)
                delay(200)
                _changedLocale.emit(locale)
            }
        }
    }
}

@Keep
sealed interface AppLocaleUiState {
    object Loading : AppLocaleUiState
    data class Success(
        val appLocale: List<AppLocale>,
        val selectedAppLocale: AppLocale
    ) : AppLocaleUiState


}