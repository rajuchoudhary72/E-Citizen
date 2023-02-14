package com.app.ecitizen.features.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ecitizen.R
import com.app.ecitizen.data.datastore.ECitizenPreferencesDataStore
import com.app.ecitizen.features.home.homeNavigationRoute
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
class UpdateProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val preferencesDataStore: ECitizenPreferencesDataStore,
    private val appRepository: AppRepository
) : ViewModel() {
    private val _user = preferencesDataStore.getUserDtoFlow()
    val user =
        _user.stateIn(scope = viewModelScope, started = SharingStarted.Lazily, initialValue = null)

    private var isUserRegistered = false


    private val updateProfileArgs: UpdateProfileArgs = UpdateProfileArgs(savedStateHandle)
    val mobileNumber = updateProfileArgs.mobileNumber


    private val _screenEvent = MutableSharedFlow<ScreenEvent>()
    val screenEvent = _screenEvent.asSharedFlow()

    var isLoading by mutableStateOf(false)

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
            val user = _user.first()
            isUserRegistered = user != null

            user?.let { user ->
                updateName(user.name)
                updateWard(user.ward)
                updateColony(user.colony)
            }
        }
    }


    fun updateProfile() {
        viewModelScope.launch {
            if(mobileNumber.length!=10){
                _screenEvent.emit(
                    ScreenEvent.ShowSnackbar.MessageResId(
                        R.string.mobile_validation_msg
                    )
                )
                return@launch
            }

            if(ward.isEmpty()){
                _screenEvent.emit(
                    ScreenEvent.ShowSnackbar.MessageResId(
                        R.string.ward_validation_msg
                    )
                )
                return@launch
            }

            if(colony.isEmpty()){
                _screenEvent.emit(
                    ScreenEvent.ShowSnackbar.MessageResId(
                        R.string.colony_validation_msg
                    )
                )
                return@launch
            }


            if(isUserRegistered){
                appRepository
                    .updateUserProfile(
                        ward = ward,
                        name = name,
                        colony = colony
                    )

            }else{
                appRepository
                    .crateUserProfile(
                        mobileNumber = mobileNumber,
                        ward = ward,
                        name = name,
                        colony = colony
                    )
            }
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

                        if(isUserRegistered){
                            _screenEvent.emit(ScreenEvent.Navigate(profileScreenNavigationRoute))
                        }else{
                            _screenEvent.emit(ScreenEvent.Navigate(homeNavigationRoute))
                        }

                    }
                }
        }
    }

}


