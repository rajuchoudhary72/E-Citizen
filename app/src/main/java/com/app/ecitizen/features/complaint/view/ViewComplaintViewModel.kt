package com.app.ecitizen.features.complaint.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ecitizen.data.network.dto.Complaint
import com.app.ecitizen.model.AppError
import com.app.ecitizen.model.LoadState
import com.app.ecitizen.model.ScreenEvent
import com.app.ecitizen.model.repository.AppRepository
import com.app.ecitizen.utils.toAppError
import com.app.ecitizen.utils.toLoadingState
import com.app.ecitizen.utils.toScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewComplaintViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private val _screenEvent = MutableSharedFlow<ScreenEvent>()
    val screenEvent = _screenEvent.asSharedFlow()

    private val _loadingComplaintId = MutableSharedFlow<String?>()
    val loadingComplaintId = _loadingComplaintId.asSharedFlow()

    private val retry = MutableStateFlow(false)

    val uiState =
        retry.flatMapLatest {
            appRepository
                .getComplaints()
        }
            .toLoadingState()
            .flowOn(Dispatchers.IO)
            .map { state ->
                when (state) {
                    LoadState.Loading -> {
                        ViewComplaintUiState.Loading
                    }

                    is LoadState.Error -> {
                        ViewComplaintUiState.Error(state.e.toAppError()!!)
                    }

                    is LoadState.Loaded -> {
                        ViewComplaintUiState.Success(state.value)
                    }
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ViewComplaintUiState.Loading
            )

    fun closeComplaint(complaint: Complaint) {
        viewModelScope.launch {
            appRepository
                .closeComplaint(complaint.id)
                .toLoadingState()
                .flowOn(Dispatchers.IO)
                .collectLatest { state ->
                    if (state.isLoading) {
                        _loadingComplaintId.emit(complaint.id)
                    } else {
                        _loadingComplaintId.emit(null)
                    }
                    state.getAppErrorIfExists()?.toScreenEvent()?.let { appError ->
                        _screenEvent.emit(
                            appError
                        )
                    }
                    state.getValueOrNull()?.let { message ->
                        _screenEvent.emit(
                            ScreenEvent.ShowSnackbar.MessageString(
                                message
                            )
                        )
                        retry.update { true }
                    }
                }
        }
    }
}

sealed interface ViewComplaintUiState {

    object Loading : ViewComplaintUiState

    data class Error(val appError: AppError) : ViewComplaintUiState

    data class Success(
        val complaints: List<Complaint>
    ) : ViewComplaintUiState
}



