package com.app.ecitizen.features.service

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ecitizen.R
import com.app.ecitizen.data.network.dto.ServiceDto
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
class ServiceViewModel @Inject constructor(
    appRepository: AppRepository
) : ViewModel() {

    val uiState = appRepository
        .getServicesData()
        .toLoadingState()
        .flowOn(Dispatchers.IO)
        .map { state ->
            when (state) {
                LoadState.Loading -> {
                    ServiceUiState.Loading
                }

                is LoadState.Error -> {
                    ServiceUiState.Error(state.e.toAppError()!!)
                }

                is LoadState.Loaded -> {
                    ServiceUiState.Success(buildService(state.value))
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ServiceUiState.Loading
        )

    private fun buildService(service: ServiceDto): List<Service> {
       return mutableListOf(
            Service(
                imgRes = R.drawable.ser1,
                nameRes = R.string.service1,
                url = service.buildingPlanApprove
            ),
            Service(
                imgRes = R.drawable.ser2,
                nameRes = R.string.service2,
                url = service.buildingPlanApprove
            ),
            Service(
                imgRes = R.drawable.ser3,
                nameRes = R.string.service3,
                url = service.buildingPlanApprove
            ),
            Service(
                imgRes = R.drawable.ser4,
                nameRes = R.string.service4,
                url = service.buildingPlanApprove
            ),
            Service(
                imgRes = R.drawable.ser5,
                nameRes = R.string.service5,
                url = service.buildingPlanApprove
            ),
            Service(
                imgRes = R.drawable.ser6,
                nameRes = R.string.service6,
                url = service.buildingPlanApprove
            ),
            Service(
                imgRes = R.drawable.ser7,
                nameRes = R.string.service7,
                url = service.buildingPlanApprove
            ),
            Service(
                imgRes = R.drawable.ser8,
                nameRes = R.string.service8,
                url = service.buildingPlanApprove
            ),
            Service(
                imgRes = R.drawable.ser9,
                nameRes = R.string.service9,
                url = service.buildingPlanApprove
            ),
            Service(
                imgRes = R.drawable.ser10,
                nameRes = R.string.service10,
                url = service.buildingPlanApprove
            ),
            Service(
                imgRes = R.drawable.ser11,
                nameRes = R.string.service11,
                url = service.buildingPlanApprove
            ),
            Service(
                imgRes = R.drawable.ser12,
                nameRes = R.string.service12,
                url = service.buildingPlanApprove
            ),
            Service(
                imgRes = R.drawable.ser13,
                nameRes = R.string.service13,
                url = service.buildingPlanApprove
            ),
            Service(
                imgRes = R.drawable.ser14,
                nameRes = R.string.service14,
                url = service.buildingPlanApprove
            ),
            Service(
                imgRes = R.drawable.ser3,
                nameRes = R.string.service15,
                url = service.buildingPlanApprove
            ),
            Service(
                imgRes = R.drawable.ser16,
                nameRes = R.string.service16,
                url = service.buildingPlanApprove
            ),
        )
    }

}

data class Service(
    val imgRes: Int,
    val nameRes: Int,
    val url: String? = null
)

sealed interface ServiceUiState {

    object Loading : ServiceUiState

    data class Error(val appError: AppError) : ServiceUiState

    data class Success(
        val services: List<Service>
    ) : ServiceUiState
}


