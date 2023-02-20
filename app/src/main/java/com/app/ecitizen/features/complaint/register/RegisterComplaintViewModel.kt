package com.app.ecitizen.features.complaint.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ecitizen.model.ScreenEvent
import com.app.ecitizen.model.repository.AppRepository
import com.app.ecitizen.utils.toLoadingState
import com.app.ecitizen.utils.toScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class RegisterComplaintViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {


    private val _screenEvent = MutableSharedFlow<ScreenEvent>()
    val screenEvent = _screenEvent.asSharedFlow()

    var isLoading by mutableStateOf(false)
        private set
    var wardNumber by mutableStateOf("")
        private set
    var complaintType by mutableStateOf("")
        private set

    var houseNumber by mutableStateOf("")
        private set

    var colonyName by mutableStateOf("")
        private set

    var streetName by mutableStateOf("")
        private set

    var note by mutableStateOf("")
        private set

    private var complaintNo by mutableStateOf(0)

    var image by mutableStateOf<File?>(null)
        private set


    fun updateWardNumber(wardNumber: String) {
        this.wardNumber = wardNumber
    }

    fun updateComplaintType(complaintType: String) {
        this.complaintType = complaintType
    }

    fun updateComplaintNo(complaintNo: Int) {
        this.complaintNo = complaintNo
    }

    fun updateHouseNumber(houseNumber: String) {
        this.houseNumber = houseNumber
    }

    fun updateColonyName(colonyName: String) {
        this.colonyName = colonyName
    }

    fun updateStreetName(streetName: String) {
        this.streetName = streetName
    }

    fun updateNote(note: String) {
        this.note = note
    }

    fun updateImage(image: File) {
        this.image = image
    }

    fun registerComplaint() {
        viewModelScope.launch {

            val complaintSerialNumber = String.format("%02d", complaintNo.plus(1))

            appRepository
                .registerCompliant(
                    headline = complaintType,
                    colony = colonyName,
                    complaintNo = complaintSerialNumber,
                    houseNo = houseNumber,
                    note = note,
                    photo = image!!,
                    street = streetName,
                    ward = wardNumber,
                    complainTypeSrNo = complaintNo.plus(1)
                )
                .toLoadingState()
                .flowOn(Dispatchers.IO)
                .collectLatest { state ->
                    isLoading = state.isLoading
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

                        _screenEvent.emit(ScreenEvent.Navigate(""))
                    }
                }
        }
    }
}


