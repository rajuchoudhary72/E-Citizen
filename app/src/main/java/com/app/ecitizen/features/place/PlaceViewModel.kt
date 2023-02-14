package com.app.ecitizen.features.place

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ecitizen.R
import com.app.ecitizen.data.network.dto.PlaceDto
import com.app.ecitizen.model.AppError
import com.app.ecitizen.model.repository.AppRepository
import com.app.ecitizen.utils.toLoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private val _visitPlaceDto = MutableSharedFlow<PlaceDto>()
    val visitPlaceDto = _visitPlaceDto.asSharedFlow()

    val uiState =
        MutableStateFlow(ImportantPlaceUiState.Success(ImportantPlace.IMPORTANT_PLACES))
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = ImportantPlaceUiState.Loading
            )


    fun visitPlace(place: ImportantPlace) {
        viewModelScope.launch {
            appRepository
                .getImportantPlaces(place.searchKey)
                .toLoadingState()
                .flowOn(Dispatchers.IO)
                .collectLatest { state ->

                    state.getValueOrNull()?.let { data: List<PlaceDto> ->
                        data.firstOrNull()?.let { _visitPlaceDto.emit(it) }
                    }
                }
        }
    }

}

data class ImportantPlace(
    @DrawableRes val icon: Int,
    @StringRes val name: Int,
    val searchKey: String
) {
    companion object {
        val IMPORTANT_PLACES = mutableListOf(
            ImportantPlace(
                icon = R.drawable.indra_rasoli,
                name = R.string.indra_rasoi,
                searchKey = "Indira Rasoi Meals @ 8 Rs"
            ),
            ImportantPlace(
                icon = R.drawable.public_park,
                name = R.string.public_parks,
                searchKey = "Public Parks"
            ),
            ImportantPlace(
                icon = R.drawable.parking,
                name = R.string.parking,
                searchKey = "parking"
            ),
            ImportantPlace(
                icon = R.drawable.public_toilt,
                name = R.string.public_toilets,
                searchKey = "Public Toilets"
            ),
            ImportantPlace(
                icon = R.drawable.commodity_hall,
                name = R.string.community_halls,
                searchKey = "Public Toilets"
            ),
            ImportantPlace(
                icon = R.drawable.asray_setal,
                name = R.string.aashray_sthal,
                searchKey = "Aashray Sthal"
            ),
            ImportantPlace(
                icon = R.drawable.torieas_places,
                name = R.string.tourism_places,
                searchKey = "Tourism Places"
            ),
        )
    }
}

sealed interface ImportantPlaceUiState {

    object Loading : ImportantPlaceUiState

    data class Error(val appError: AppError) : ImportantPlaceUiState

    data class Success(
        val places: List<ImportantPlace>
    ) : ImportantPlaceUiState
}


