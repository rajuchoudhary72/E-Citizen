package com.app.ecitizen.ui

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ecitizen.data.datastore.ECitizenPreferencesDataStore
import com.app.ecitizen.data.network.dto.AppFront
import com.app.ecitizen.features.advertisement.AdvertisementScreenUiState
import com.app.ecitizen.model.LoadState
import com.app.ecitizen.model.repository.AppRepository
import com.app.ecitizen.utils.toLoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    appRepository: AppRepository,
    preferencesDataStore: ECitizenPreferencesDataStore
) : ViewModel() {

    val uiState = appRepository
        .getAppFronts()
        .flowOn(Dispatchers.IO)
        .toLoadingState()
        .map { state ->
            when (state) {
                is LoadState.Error -> {
                    MainActivityUiState.Error(state.e)
                }

                is LoadState.Loaded -> {
                    state.value.firstOrNull()?.let {
                        preferencesDataStore.saveAppFront(it)
                    }
                    MainActivityUiState.Success(
                        appFront = state.value.firstOrNull()
                    )
                }

                LoadState.Loading -> {
                    MainActivityUiState.Loading
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MainActivityUiState.Loading
        )

}


@Keep
sealed interface MainActivityUiState {
    object Loading : MainActivityUiState

    data class Error(val error: Throwable) : MainActivityUiState

    data class Success(val appFront: AppFront?) : MainActivityUiState
}