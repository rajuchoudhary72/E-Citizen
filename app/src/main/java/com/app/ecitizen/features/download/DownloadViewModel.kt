package com.app.ecitizen.features.download

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ecitizen.data.network.dto.DownloadDto
import com.app.ecitizen.data.network.dto.NoticeDto
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
class DownloadViewModel @Inject constructor(
    appRepository: AppRepository
) : ViewModel(){

    val uiState = appRepository
        .getDownloads()
        .toLoadingState()
        .flowOn(Dispatchers.IO)
        .map { state ->
            when (state) {
                LoadState.Loading -> {
                    DownloadUiState.Loading
                }

                is LoadState.Error -> {
                    DownloadUiState.Error(state.e.toAppError()!!)
                }

                is LoadState.Loaded -> {
                    DownloadUiState.Success(state.value)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DownloadUiState.Loading
        )

}

sealed interface DownloadUiState {

    object Loading : DownloadUiState

    data class Error(val appError: AppError) : DownloadUiState

    data class Success(
        val downloads: List<DownloadDto>
    ) : DownloadUiState
}
