package com.app.ecitizen.features.contact

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ecitizen.R
import com.app.ecitizen.model.AppError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ContactUSViewModel @Inject constructor(
) : ViewModel() {

    val uiState =
        MutableStateFlow(ContactUsUiState.Success(ContactUs.CONTACTS))
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = ContactUsUiState.Loading
            )
}


data class ContactUs(
    @DrawableRes val icon: Int,
    @StringRes val name: Int,
    val searchKey: String
) {
    companion object {
        val CONTACTS = mutableListOf(
            ContactUs(
                icon = R.drawable.about_us_emp,
                name = R.string.chairman,
                searchKey = "Chairman"
            ),
            ContactUs(
                icon = R.drawable.about_us_emp,
                name = R.string.commissioner,
                searchKey = "Commissioner"
            ),
            ContactUs(
                icon = R.drawable.about_us_emp,
                name = R.string.office_employees,
                searchKey = "Office employees"
            ),
            ContactUs(
                icon = R.drawable.about_us_emp,
                name = R.string.office_a_glance,
                searchKey = "Office a Glance"
            ),
        )
    }
}


sealed interface ContactUsUiState {

    object Loading : ContactUsUiState

    data class Error(val appError: AppError) : ContactUsUiState

    data class Success(
        val contactUs: List<ContactUs>
    ) : ContactUsUiState
}



