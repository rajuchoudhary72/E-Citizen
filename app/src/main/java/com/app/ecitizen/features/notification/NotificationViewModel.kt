package com.app.ecitizen.features.notification

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ecitizen.data.network.dto.AdvertisementDto
import com.app.ecitizen.data.network.dto.NotificationDto
import com.app.ecitizen.features.advertisement.AdvertisementScreenUiState
import com.app.ecitizen.model.AppError
import com.app.ecitizen.model.LoadState
import com.app.ecitizen.model.repository.AppRepository
import com.app.ecitizen.utils.toAppError
import com.app.ecitizen.utils.toLoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    appRepository: AppRepository
) : ViewModel(){

    val uiState: StateFlow<NotificationScreenUiState> = appRepository
        .getNotifications()
        .toLoadingState()
        .flowOn(Dispatchers.IO)
        .map { state ->
            when (state) {
                LoadState.Loading -> {
                    NotificationScreenUiState.Loading
                }

                is LoadState.Error -> {
                    NotificationScreenUiState.Error(state.e.toAppError())
                }

                is LoadState.Loaded -> {
                    NotificationScreenUiState.Success(state.value)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NotificationScreenUiState.Loading
        )
}

@Keep
sealed interface NotificationScreenUiState {
    object Loading : NotificationScreenUiState

    data class Error(val appError: AppError?) : NotificationScreenUiState

    data class Success(val notifications: List<NotificationDto>):NotificationScreenUiState
}