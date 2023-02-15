package com.app.ecitizen.features.contact.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ecitizen.data.network.dto.AboutUsDto
import com.app.ecitizen.data.network.dto.NoticeDto
import com.app.ecitizen.data.network.dto.PhoneBookDto
import com.app.ecitizen.features.contact.ContactUsArgs
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
class ContactViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    appRepository: AppRepository
) : ViewModel(){
    private val contactUsArgs: ContactUsArgs = ContactUsArgs(savedStateHandle)
    val type = contactUsArgs.type


    val uiState = appRepository
        .getContactUs(type)
        .toLoadingState()
        .flowOn(Dispatchers.IO)
        .map { state ->
            when (state) {
                LoadState.Loading -> {
                    ContactUiState.Loading
                }

                is LoadState.Error -> {
                    ContactUiState.Error(state.e.toAppError()!!)
                }

                is LoadState.Loaded -> {
                    ContactUiState.Success(state.value)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ContactUiState.Loading
        )
}


sealed interface ContactUiState {

    object Loading : ContactUiState

    data class Error(val appError: AppError) : ContactUiState

    data class Success(
        val phones: List<AboutUsDto>
    ) : ContactUiState
}



