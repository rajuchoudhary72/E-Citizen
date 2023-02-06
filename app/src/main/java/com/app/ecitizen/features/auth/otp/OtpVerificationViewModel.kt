package com.app.ecitizen.features.auth.otp

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ecitizen.features.auth.OtpVerificationArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class OtpVerificationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val otpVerificationArgs: OtpVerificationArgs = OtpVerificationArgs(savedStateHandle)

    val mobileNumber = otpVerificationArgs.mobileNumber

    private val _otp = MutableStateFlow("1234")
    val otp = _otp.asStateFlow()

    val isMobileValid = _otp
        .map { it.length == 4 }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    fun updateOtp(otp: String) {
        _otp.update { otp }
    }


    fun verifyOtp() {

    }

    fun resendOtp() {

    }

}