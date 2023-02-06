package com.app.ecitizen.features.complaint.register

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterComplaintViewModel @Inject constructor(
) : ViewModel() {
    var wardNumber by mutableStateOf("")
        private set

    var houseNumber by mutableStateOf("")
        private set

    var colonyName by mutableStateOf("")
        private set

    var streetName by mutableStateOf("")
        private set

    var note by mutableStateOf("")
        private set

    var image by mutableStateOf<Uri?>(null)
        private set


    fun updateWardNumber(wardNumber: String) {
        this.wardNumber = wardNumber
    }

    fun updateHouseNumber(houseNumber: String) {
        this.houseNumber = houseNumber
    }

    fun updateColonyName(colonyName: String) {
        this.colonyName = colonyName
    }

    fun updateStreetName(streetName: String) {
        this.streetName = streetName
    }

    fun updateNote(note: String) {
        this.note = note
    }

    fun updateImage(image: Uri) {
        this.image = image
    }
}


