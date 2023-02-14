package com.app.ecitizen.features.imagePreview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.app.ecitizen.features.auth.OtpVerificationArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImagePreviewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel(){
   private val imageArgs: ImagePreviewArgs = ImagePreviewArgs(savedStateHandle)
   val imagePath = imageArgs.image
}
