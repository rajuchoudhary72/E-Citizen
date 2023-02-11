package com.app.ecitizen.features.profile

import androidx.lifecycle.ViewModel
import com.app.ecitizen.data.datastore.ECitizenPreferencesDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    val preferencesDataStore: ECitizenPreferencesDataStore
) : ViewModel(){
   private val _user = preferencesDataStore.getUserDtoFlow()
    val user = _user

}


