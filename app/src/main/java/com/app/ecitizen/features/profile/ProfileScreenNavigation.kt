package com.app.ecitizen.features.profile

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.app.ecitizen.features.auth.navigateToOtpVerification
import com.app.ecitizen.features.auth.otpVerificationNavigationRoute


const val profileScreenNavigationRoute = "profile_screen_route"
const val updateProfileScreenNavigationRoute = "update_profile_screen_route"

internal const val mobileNumberArg = "mobileNumber"


internal class UpdateProfileArgs(val mobileNumber: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(Uri.decode(checkNotNull(savedStateHandle[mobileNumberArg])))
}

fun NavController.navigateToProfile(navOptions: NavOptions? = null) {
    this.navigate(profileScreenNavigationRoute, navOptions)
}

fun NavController.navigateToUpdateProfile(mobileNumber: String) {
    val encodedMobileNumber = Uri.encode(mobileNumber)
    this.navigate("$updateProfileScreenNavigationRoute/$encodedMobileNumber")
}

fun NavGraphBuilder.profileScreen(
    onBackClick: () -> Unit,
    navigateToUpdateProfile: (String) -> Unit,
    navigateToHome: () -> Unit,
) {
    composable(route = profileScreenNavigationRoute) {
        ProfileScreenRoute(
            onBackClick = onBackClick,
            navigateToUpdateProfile = navigateToUpdateProfile
        )
    }
    composable(  route = "$updateProfileScreenNavigationRoute/{${mobileNumberArg}}") {
        UpdateProfileScreenRoute(
            onBackClick = onBackClick,
            navigateToHome = navigateToHome
        )
    }
}

