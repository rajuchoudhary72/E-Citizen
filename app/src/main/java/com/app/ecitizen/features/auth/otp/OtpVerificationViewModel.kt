package com.app.ecitizen.features.auth.otp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ecitizen.R
import com.app.ecitizen.features.auth.OtpVerificationArgs
import com.app.ecitizen.features.home.homeNavigationRoute
import com.app.ecitizen.features.profile.updateProfileScreenNavigationRoute
import com.app.ecitizen.model.LoadState
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
import javax.inject.Inject

@HiltViewModel
class OtpVerificationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authRepository: AppRepository
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
                .verifyOtp(mobileNumber, otp)
                .toLoadingState()
                .flowOn(Dispatchers.IO)
                .collectLatest { state ->
                    otpVerificationLoading = state.isLoading
                    state.getAppErrorIfExists()?.toScreenEvent()?.let { appError ->
                        _screenEvent.emit(
                            appError
                        )
                    }

                    if (state is LoadState.Loaded) {
                        val user = state.getValueOrNull()

                        _screenEvent.emit(
                            ScreenEvent.ShowSnackbar.MessageResId(
                              R.string.otp_match_successfully
                            )
                        )

                        if (user == null) {
                            _screenEvent.emit(
                                ScreenEvent.Navigate(
                                    updateProfileScreenNavigationRoute
                                )
                            )
                        } else {
                            _screenEvent.emit(ScreenEvent.Navigate(homeNavigationRoute))
                        }


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