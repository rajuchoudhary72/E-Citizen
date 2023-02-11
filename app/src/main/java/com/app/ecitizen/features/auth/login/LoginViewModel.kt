package com.app.ecitizen.features.auth.login


import androidx.annotation.Keep
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ecitizen.features.auth.loginNavigationRoute
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

import com.app.ecitizen.R

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AppRepository
) : ViewModel() {

    private val _screenEvent = MutableSharedFlow<ScreenEvent>()
    val screenEvent = _screenEvent.asSharedFlow()
    var mobileNumber: String by mutableStateOf("")
        private set

    var isLoading: Boolean by mutableStateOf(false)
        private set


    fun updateMobileNumber(mobileNumber: String) {
        this.mobileNumber = mobileNumber
    }

    fun sendOtp() {
        viewModelScope.launch {

            if(mobileNumber.length!=10){
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
                        _screenEvent.emit(ScreenEvent.Navigate(loginNavigationRoute))
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