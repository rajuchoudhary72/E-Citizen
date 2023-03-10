package com.app.ecitizen.features.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ecitizen.data.datastore.ECitizenPreferencesDataStore
import com.app.ecitizen.features.home.homeNavigationRoute
import com.app.ecitizen.features.splash.splashScreenNavigationRoute
import com.app.ecitizen.model.ScreenEvent
import com.app.ecitizen.model.repository.AppRepository
import com.app.ecitizen.utils.toLoadingState
import com.app.ecitizen.utils.toScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    val preferencesDataStore: ECitizenPreferencesDataStore,
    private val appRepository: AppRepository
) : ViewModel() {
    private val _user = preferencesDataStore.getUserDtoFlow()
    val user =
        _user.stateIn(scope = viewModelScope, started = SharingStarted.Lazily, initialValue = null)




    private val _screenEvent = MutableSharedFlow<ScreenEvent>()
    val screenEvent = _screenEvent.asSharedFlow()

    var isLoading by mutableStateOf(false)

    var mobileNumber by mutableStateOf("")
        private set
    var name by mutableStateOf("")
        private set

    var ward by mutableStateOf("")
        private set

    var colony by mutableStateOf("")
        private set


    fun updateName(name: String) {
        this.name = name
    }

    fun updateWard(ward: String) {
        this.ward = ward
    }

    fun updateColony(colony: String) {
        this.colony = colony
    }


    init {
        viewModelScope.launch {
            _user.first()?.let { user ->
                mobileNumber = user.mobile
                updateName(user.name)
                updateWard(user.ward)
                updateColony(user.colony)
            }
        }
    }


    fun updateProfile() {
        viewModelScope.launch {
            appRepository
                .crateUserProfile(
                    mobileNumber = mobileNumber,
                    ward = ward,
                    name = name,
                    colony = colony
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
                                "Otp match successfully !"
                            )
                        )

                        _screenEvent.emit(ScreenEvent.Navigate(homeNavigationRoute))

                    }
                }
        }
    }

    fun logout(){
        viewModelScope.launch {
            preferencesDataStore.clearPreferences()
            _screenEvent.emit(ScreenEvent.Navigate(splashScreenNavigationRoute))
        }
    }

}


