package com.app.ecitizen.features.auth.otp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ecitizen.features.auth.OtpVerificationArgs
import com.app.ecitizen.features.auth.loginNavigationRoute
import com.app.ecitizen.features.home.homeNavigationRoute
import com.app.ecitizen.model.ScreenEvent
import com.app.ecitizen.model.repository.AuthRepository
import com.app.ecitizen.utils.stringRes
import com.app.ecitizen.utils.toLoadingState
import com.app.ecitizen.utils.toScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpVerificationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val otpVerificationArgs: OtpVerificationArgs = OtpVerificationArgs(savedStateHandle)
    val mobileNumber = otpVerificationArgs.mobileNumber

    var otp: String by mutableStateOf("")

    private val _screenEvent = MutableSharedFlow<ScreenEvent>()
    val screenEvent = _screenEvent.asSharedFlow()

    var otpVerificationLoading: Boolean by mutableStateOf(false)
        private set

    var otpResendLoading: Boolean by mutableStateOf(false)
        private set

    fun updateOtp(otp: String) {
        this.otp = otp
    }

    fun verifyOtp() {
        viewModelScope.launch {
            authRepository
                .verifyOtp(mobileNumber, otp )
                .toLoadingState()
                .flowOn(Dispatchers.IO)
                .collectLatest { state ->
                    otpVerificationLoading = state.isLoading
                    state.getAppErrorIfExists()?.toScreenEvent()?.let { appError ->
                        _screenEvent.emit(
                            appError
                        )
                    }

                    state.getValueOrNull()?.let { message ->

                        _screenEvent.emit(
                            ScreenEvent.ShowSnackbar.MessageString(
                                "Otp match successfully !"
                            )
                        )

                        _screenEvent.emit(ScreenEvent.Navigate(homeNavigationRoute))

                    }
                }
        }
    }

    fun resendOtp() {
        viewModelScope.launch {
            authRepository
                .sendOtp(mobileNumber)
                .toLoadingState()
                .flowOn(Dispatchers.IO)
                .collectLatest { state ->
                    otpResendLoading = state.isLoading

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
                    }
                }
        }
    }

}