package com.app.ecitizen.serviceProviderFeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ecitizen.data.network.dto.Complaint
import com.app.ecitizen.model.AppError
import com.app.ecitizen.model.LoadState
import com.app.ecitizen.model.repository.AppRepository
import com.app.ecitizen.utils.toAppError
import com.app.ecitizen.utils.toLoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ServiceProviderComplaintViewModel @Inject constructor(
    appRepository: AppRepository
) : ViewModel() {

    private val status = MutableStateFlow("1")

    fun updateStats(status:String){
        this.status.update { status }
    }

    val uiState =
        appRepository
            .getServiceProviderComplaints("1")
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

}

sealed interface ViewComplaintUiState {

    object Loading : ViewComplaintUiState

    data class Error(val appError: AppError) : ViewComplaintUiState

    data class Success(
        val complaints: List<Complaint>
    ) : ViewComplaintUiState
}