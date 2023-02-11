package com.app.ecitizen.features.advertisement

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ecitizen.data.network.dto.AdvertisementDto
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
class AdvertisementViewModel @Inject constructor(
    private val authRepository: AppRepository
) : ViewModel() {

    val uiState:StateFlow<AdvertisementScreenUiState> = authRepository
        .getAdvertisement()
        .toLoadingState()
        .flowOn(Dispatchers.IO)
        .map { state ->
            when (state) {
                LoadState.Loading -> {
                    AdvertisementScreenUiState.Loading
                }

                is LoadState.Error -> {
                    AdvertisementScreenUiState.Error(state.e.toAppError())
                }

                is LoadState.Loaded -> {
                    AdvertisementScreenUiState.Success(state.value)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AdvertisementScreenUiState.Loading
        )


}
@Keep
sealed interface AdvertisementScreenUiState {
    object Loading : AdvertisementScreenUiState

    data class Error(val appError: AppError?) : AdvertisementScreenUiState

    data class Success(val advertisements: List<AdvertisementDto>):AdvertisementScreenUiState
}


