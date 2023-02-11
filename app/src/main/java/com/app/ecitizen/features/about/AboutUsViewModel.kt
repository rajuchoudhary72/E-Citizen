package com.app.ecitizen.features.about

import androidx.annotation.Keep
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ecitizen.data.datastore.ECitizenPreferencesDataStore
import com.app.ecitizen.data.network.dto.AppFront
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AboutUsViewModel @Inject constructor(
   preferencesDataStore: ECitizenPreferencesDataStore
) : ViewModel() {


}


