package com.app.ecitizen.features.home

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.Keep
import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ecitizen.R
import com.app.ecitizen.data.network.dto.SliderImage
import com.app.ecitizen.model.LoadState
import com.app.ecitizen.model.repository.AppRepository
import com.app.ecitizen.ui.theme.Blue80
import com.app.ecitizen.ui.theme.DarkGreen80
import com.app.ecitizen.ui.theme.DarkPurpleGray90
import com.app.ecitizen.ui.theme.Green80
import com.app.ecitizen.ui.theme.Orange80
import com.app.ecitizen.ui.theme.Purple80
import com.app.ecitizen.ui.theme.Red80
import com.app.ecitizen.ui.theme.Teal80
import com.app.ecitizen.utils.toLoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    appRepository: AppRepository
) : ViewModel() {
    var shouldShowComplaintDialog by mutableStateOf(false)
        private set

    val homeUiState: StateFlow<HomeUiState> =
        appRepository
            .getSliderImages()
            .flowOn(Dispatchers.IO)
            .toLoadingState()
            .map { state ->
                when (state) {
                    is LoadState.Error -> {
                        HomeUiState.Error(state.e)
                    }

                    is LoadState.Loaded -> {
                        HomeUiState.Success(
                            sliderImages = state.getValueOrNull() ?: emptyList(),
                            services = Service.SERVICES
                        )

                    }

                    LoadState.Loading -> {
                        HomeUiState.Loading
                    }
                }

            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = HomeUiState.Loading
            )


    fun setShowComplaintDialog(show: Boolean) {
        shouldShowComplaintDialog = show
    }

}

data class Service(
    @StringRes val name: Int,
    val description: Int,
    val backgroundColor: Color,
    @DrawableRes val icon: Int
) {

    companion object {
        val SERVICES = mutableListOf(

            Service(
                name = R.string.notice_board,
                description = R.string.announcement_or_intimation_of_something_happening_in_office,
                backgroundColor = Blue80,
                icon = R.drawable.outline_sticky_note_2_24
            ),
            Service(
                name = R.string.services,
                description = R.string.committed_for_disposal_of_service_in_speedy_and_transparent_manner,
                backgroundColor = DarkGreen80,
                icon = R.drawable.outline_miscellaneous_services_24
            ),
            Service(
                name = R.string.downloads,
                description = R.string.citizen_can_download_tne_application_form_here,
                backgroundColor = Teal80,
                icon = R.drawable.outline_cloud_download_24
            ),
            Service(
                name = R.string.important_places,
                description = R.string.citizen_can_check_important_places_within_in_our_nagar_palika,
                backgroundColor = DarkPurpleGray90,
                icon = R.drawable.outline_place_24
            ),
            Service(
                name = R.string.register_complaints,
                description = R.string.register_you_complaint_so_we_can_serve_you_better,
                backgroundColor = Green80,
                icon = R.drawable.baseline_app_registration_24
            ),
            Service(
                name = R.string.advertisements,
                description = R.string.you_can_watch_advertisement_of_information_and_schemes_to_citizens,
                backgroundColor = Orange80,
                icon = R.drawable.outline_live_tv_24
            ),
            Service(
                name = R.string.telephone_directory,
                description = R.string.check_telephone_directory_of_government_officials_office_and_ward_members,
                backgroundColor = Purple80,
                icon = R.drawable.outline_contact_phone_24
            ),
            Service(
                name = R.string.about_us,
                description = R.string.check_more_about_us,
                backgroundColor = Red80,
                icon = R.drawable.outline_account_circle_24
            )
        )
    }

}

@Keep
sealed interface HomeUiState {
    object Loading : HomeUiState

    data class Error(val error: Throwable) : HomeUiState

    data class Success(
        val sliderImages: List<SliderImage>,
        val services: List<Service>
    ) : HomeUiState
}