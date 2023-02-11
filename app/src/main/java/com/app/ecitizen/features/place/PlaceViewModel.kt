package com.app.ecitizen.features.place

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ecitizen.data.network.dto.DownloadDto
import com.app.ecitizen.model.AppError
import com.app.ecitizen.model.LoadState
import com.app.ecitizen.model.repository.AppRepository
import com.app.ecitizen.utils.toAppError
import com.app.ecitizen.utils.toLoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PlaceViewModel @Inject constructor(
    appRepository: AppRepository
) : ViewModel(){

    val uiState = appRepository
        .getDownloads()
        .toLoadingState()
        .flowOn(Dispatchers.IO)
        .map { state ->
            when (state) {
                LoadState.Loading -> {
                    ImportantPlaceUiState.Loading
                }

                is LoadState.Error -> {
                    ImportantPlaceUiState.Error(state.e.toAppError()!!)
                }

                is LoadState.Loaded -> {
                    ImportantPlaceUiState.Success(state.value)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ImportantPlaceUiState.Loading
        )

}

sealed interface ImportantPlaceUiState {

    object Loading : ImportantPlaceUiState

    data class Error(val appError: AppError) : ImportantPlaceUiState

    data class Success(
        val downloads: List<DownloadDto>
    ) : ImportantPlaceUiState
}


