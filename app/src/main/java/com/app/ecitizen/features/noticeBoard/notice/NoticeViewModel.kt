package com.app.ecitizen.features.noticeBoard.notice

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ecitizen.data.network.dto.NoticeDto
import com.app.ecitizen.features.noticeBoard.NoticeBoardArgs
import com.app.ecitizen.features.notification.NotificationScreenUiState
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
class NoticeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    appRepository: AppRepository
) : ViewModel() {

    private val noticeBoardArgs: NoticeBoardArgs = NoticeBoardArgs(savedStateHandle)
    val noticeType = noticeBoardArgs.noticeType

    val uiState: StateFlow<NoticeUiState> = appRepository
        .getNotices(noticeType)
        .toLoadingState()
        .flowOn(Dispatchers.IO)
        .map { state ->
            when (state) {
                LoadState.Loading -> {
                    NoticeUiState.Loading
                }

                is LoadState.Error -> {
                    NoticeUiState.Error(state.e.toAppError()!!)
                }

                is LoadState.Loaded -> {
                    NoticeUiState.Success(state.value)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NoticeUiState.Loading
        )

}

sealed interface NoticeUiState {

    object Loading : NoticeUiState

    data class Error(val appError: AppError) : NoticeUiState

    data class Success(
        val notices: List<NoticeDto>
    ) : NoticeUiState
}


