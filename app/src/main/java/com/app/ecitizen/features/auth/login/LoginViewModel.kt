package com.app.ecitizen.features.auth.login


import androidx.annotation.Keep
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ecitizen.R
import com.app.ecitizen.features.auth.otpVerificationNavigationRoute
import com.app.ecitizen.features.home.homeNavigationRoute
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
class LoginViewModel @Inject constructor(
    private val authRepository: AppRepository
) : ViewModel() {

    private val _screenEvent = MutableSharedFlow<ScreenEvent>()
    val screenEvent = _screenEvent.asSharedFlow()

    var loginAsCitizen: Boolean by mutableStateOf(true)
        private set
    var mobileNumber: String by mutableStateOf("")
        private set
    var password: String by mutableStateOf("")
        private set

    var isLoading: Boolean by mutableStateOf(false)
        private set


    fun updateMobileNumber(mobileNumber: String) {
        this.mobileNumber = mobileNumber
    }

    fun updatePassword(password: String) {
        this.password = password
    }

    fun loginAsCitizen() {
        this.loginAsCitizen = loginAsCitizen.not()
    }


    fun sendOtp() {
        viewModelScope.launch {

            if (loginAsCitizen.not()) {
                loginAsServiceProvider()
                return@launch
            }

            if (mobileNumber.length != 10) {
                _screenEvent.emit(
                    ScreenEvent.ShowSnackbar.MessageResId(R.string.mobile_validation_msg)
                )

                return@launch
            }
            authRepository
                .sendOtp(mobileNumber)
                .toLoadingState()
                .flowOn(Dispatchers.IO)
                .collectLatest { state ->
                    isLoading = state.isLoading

                    state.getAppErrorIfExists()?.toScreenEvent()?.let { screenEvent ->
                        _screenEvent.emit(
                            screenEvent
                        )
                    }

                    state.getValueOrNull()?.let { message ->
                        _screenEvent.emit(
                            ScreenEvent.ShowSnackbar.MessageString(
                                message
                            )
                        )
                        _screenEvent.emit(ScreenEvent.Navigate(otpVerificationNavigationRoute))
                    }
                }
        }
    }

    private fun loginAsServiceProvider() {
        viewModelScope.launch {
            if (mobileNumber.length != 10) {
                _screenEvent.emit(
                    ScreenEvent.ShowSnackbar.MessageResId(R.string.mobile_validation_msg)
                )

                return@launch
            }

            if (password.isEmpty()) {
                _screenEvent.emit(
                    ScreenEvent.ShowSnackbar.MessageResId(R.string.password_validation_msg)
                )

                return@launch
            }
            authRepository
                .loginAsServiceProvider(mobileNumber, password)
                .toLoadingState()
                .flowOn(Dispatchers.IO)
                .collectLatest { state ->
                    isLoading = state.isLoading

                    state.getAppErrorIfExists()?.toScreenEvent()?.let { screenEvent ->
                        _screenEvent.emit(
                            screenEvent
                        )
                    }

                    state.getValueOrNull()?.let { message ->
                        _screenEvent.emit(
                            ScreenEvent.ShowSnackbar.MessageString(
                                message
                            )
                        )
                        _screenEvent.emit(ScreenEvent.Navigate(homeNavigationRoute))
                    }
                }
        }
    }

}

@Keep
sealed interface ErrorMessage {
    data class ErrorMessageId(val id: Int) : ErrorMessage
    data class MessageValue(val value: String) : ErrorMessage
}