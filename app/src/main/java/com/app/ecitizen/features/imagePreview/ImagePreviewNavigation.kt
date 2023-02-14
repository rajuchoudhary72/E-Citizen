package com.app.ecitizen.features.imagePreview

import android.net.Uri
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.app.ecitizen.features.auth.login.LoginRoute
import com.app.ecitizen.features.auth.otp.OtpVerificationRoute


const val loginNavigationRoute = "image_preview_route"

internal const val imagePathArg = "imagePath"

fun NavController.navigateToImagePreview(image: String) {
    val imagePath = Uri.encode(image)
    this.navigate("$loginNavigationRoute/$imagePath")
}
internal class ImagePreviewArgs(val image: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(Uri.decode(checkNotNull(savedStateHandle[imagePathArg])))
}


fun NavGraphBuilder.imagePreviewScreen(
    onBackClick: () -> Unit,
) {
    composable(
        route = "$loginNavigationRoute/{$imagePathArg}"
    ) {
        ImagePreviewRoute(
            onBackClick = onBackClick
        )
    }
}
