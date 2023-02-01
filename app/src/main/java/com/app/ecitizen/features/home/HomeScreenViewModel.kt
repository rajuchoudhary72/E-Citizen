package com.app.ecitizen.features.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ecitizen.R
import com.app.ecitizen.ui.theme.Blue80
import com.app.ecitizen.ui.theme.DarkGreen80
import com.app.ecitizen.ui.theme.DarkGreenGray90
import com.app.ecitizen.ui.theme.DarkPurpleGray90
import com.app.ecitizen.ui.theme.Green80
import com.app.ecitizen.ui.theme.Orange80
import com.app.ecitizen.ui.theme.Purple80
import com.app.ecitizen.ui.theme.Red80
import com.app.ecitizen.ui.theme.Teal80
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor() : ViewModel() {

    val homeUiState: StateFlow<HomeUiState> = MutableStateFlow(
        HomeUiState.Success(
            bannerImages = mutableListOf<String>("", "", "", ""),
            services = Service.SERVICES
        )
    )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState.Loading
        )

}

data class Service(
    @StringRes val name: Int,
    val description: String,
    val backgroundColor: Color,
    @DrawableRes val icon: Int
) {

    companion object {
        val SERVICES = mutableListOf(

            Service(
                name = R.string.notice_board,
                description = "Announcement or Intimation of something happening in office.",
                backgroundColor = Blue80,
                icon = R.drawable.outline_sticky_note_2_24
            ),
            Service(
                name = R.string.services,
                description = "Committed for disposal of service in speedy and transparent manner.",
                backgroundColor = DarkGreen80,
                icon = R.drawable.outline_miscellaneous_services_24
            ),
            Service(
                name = R.string.downloads,
                description = "Citizen can download tne application form here.",
                backgroundColor = Teal80,
                icon = R.drawable.outline_cloud_download_24
            ),
            Service(
                name = R.string.important_places,
                description = "Citizen can check important places within in our Nagar Palika.",
                backgroundColor = DarkPurpleGray90,
                icon = R.drawable.outline_place_24
            ),
            Service(
                name = R.string.register_complaints,
                description = "Register you complaint so we can serve you better.",
                backgroundColor = Green80,
                icon = R.drawable.baseline_app_registration_24
            ),
            Service(
                name = R.string.advertisements,
                description = "You can watch advertisement of information and schemes to Citizens",
                backgroundColor = Orange80,
                icon = R.drawable.outline_live_tv_24
            ),
            Service(
                name = R.string.telephone_directory,
                description = "Check telephone directory of government officials, office and ward members.",
                backgroundColor = Purple80,
                icon = R.drawable.outline_contact_phone_24
            ),
            Service(
                name = R.string.about_us,
                description = "Check more about us.",
                backgroundColor = Red80,
                icon = R.drawable.outline_account_circle_24
            )
        )
    }

}


sealed interface HomeUiState {
    object Loading : HomeUiState

    data class Error(val error: Throwable) : HomeUiState

    data class Success(
        val bannerImages: List<String>,
        val services: List<Service>
    ) : HomeUiState
}