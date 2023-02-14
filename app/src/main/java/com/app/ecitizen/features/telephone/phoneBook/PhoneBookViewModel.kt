package com.app.ecitizen.features.telephone.phoneBook

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ecitizen.data.network.dto.NoticeDto
import com.app.ecitizen.data.network.dto.PhoneBookDto
import com.app.ecitizen.features.noticeBoard.NoticeBoardArgs
import com.app.ecitizen.features.noticeBoard.notice.NoticeUiState
import com.app.ecitizen.features.telephone.PhoneBookArgs
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
class PhoneBookViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    appRepository: AppRepository
) : ViewModel(){
    private val phoneBookArgs: PhoneBookArgs = PhoneBookArgs(savedStateHandle)
    val phoneBookType = phoneBookArgs.phoneBookType


    val uiState = appRepository
        .getPhoneBook(phoneBookType)
        .toLoadingState()
        .flowOn(Dispatchers.IO)
        .map { state ->
            when (state) {
                LoadState.Loading -> {
                    PhoneBookUiState.Loading
                }

                is LoadState.Error -> {
                    PhoneBookUiState.Error(state.e.toAppError()!!)
                }

                is LoadState.Loaded -> {
                    PhoneBookUiState.Success(state.value)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PhoneBookUiState.Loading
        )
}


sealed interface PhoneBookUiState {

    object Loading : PhoneBookUiState

    data class Error(val appError: AppError) : PhoneBookUiState

    data class Success(
        val phones: List<PhoneBookDto>
    ) : PhoneBookUiState
}



