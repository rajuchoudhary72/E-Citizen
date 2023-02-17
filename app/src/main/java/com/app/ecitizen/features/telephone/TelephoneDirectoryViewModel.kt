package com.app.ecitizen.features.telephone

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ecitizen.R
import com.app.ecitizen.features.place.ImportantPlace
import com.app.ecitizen.model.AppError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TelephoneDirectoryViewModel @Inject constructor(
) : ViewModel(){

    val uiState =
        MutableStateFlow(TelephoneDirectoryUiState.Success(TelephoneDirectory.TELEPHONE_DIRECTORY))
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = TelephoneDirectoryUiState.Loading
            )
}



data class TelephoneDirectory(
    @DrawableRes val icon: Int,
    @StringRes val name: Int,
    val searchKey: String
) {
    companion object {
        val TELEPHONE_DIRECTORY = mutableListOf(
            TelephoneDirectory(
                icon = R.drawable.about_us_emp,
                name = R.string.chairman,
                searchKey = "chairman"
            ),
            TelephoneDirectory(
                icon = R.drawable.about_us_emp,
                name = R.string.commissioner,
                searchKey = "commissioner"
            ),
            TelephoneDirectory(
                icon = R.drawable.about_us_emp,
                name = R.string.office_employees,
                searchKey = "office_employee"
            ),
            TelephoneDirectory(
                icon = R.drawable.about_us_emp,
                name = R.string.ward_members,
                searchKey = "ward_member"
            ),
        )
    }
}


sealed interface TelephoneDirectoryUiState {

    object Loading : TelephoneDirectoryUiState

    data class Error(val appError: AppError) : TelephoneDirectoryUiState

    data class Success(
        val telephoneDirectory: List<TelephoneDirectory>
    ) : TelephoneDirectoryUiState
}



