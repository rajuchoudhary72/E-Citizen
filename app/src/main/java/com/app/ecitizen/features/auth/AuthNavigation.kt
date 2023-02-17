package com.app.ecitizen.features.auth

import android.net.Uri
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.app.ecitizen.features.auth.login.LoginRoute
import com.app.ecitizen.features.auth.otp.OtpVerificationRoute


const val loginNavigationRoute = "login_route"
const val otpVerificationNavigationRoute = "otp_verification_route"

internal const val mobileNumberArg = "mobileNumber"

fun NavController.navigateToOtpVerification(mobileNumber: String) {
    val encodedMobileNumber = Uri.encode(mobileNumber)
    this.navigate("$otpVerificationNavigationRoute/$encodedMobileNumber")
}
fun NavController.navigateToLoginScreen(navOptions: NavOptions? = null) {
    this.navigate(loginNavigationRoute, navOptions)
}

internal class OtpVerificationArgs(val mobileNumber: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(Uri.decode(checkNotNull(savedStateHandle[mobileNumberArg])))
}

fun NavGraphBuilder.loginScreen(
    snackbarHostState:() -> SnackbarHostState,
    navigateToVerifyOtp: (String) -> Unit,
    openAppLocaleSettings: () -> Unit,
    navigateToServiceProviderHome:() ->Unit

) {
    composable(route = loginNavigationRoute) {
        LoginRoute(
            snackbarHostState=snackbarHostState,
            navigateToVerifyOtp = navigateToVerifyOtp,
            openAppLocaleSettings = openAppLocaleSettings,
            navigateToServiceProviderHome = navigateToServiceProviderHome
        )
    }
}

fun NavGraphBuilder.otpVerificationScreen(
    snackbarHostState: SnackbarHostState,
    navigateToHome: () -> Unit,
    onBackClick: () -> Unit,
    navigateToUpdateProfile: (String) -> Unit,
) {
    composable(
        route = "$otpVerificationNavigationRoute/{$mobileNumberArg}"
    ) {
        OtpVerificationRoute(
            snackbarHostState=snackbarHostState,
            navigateToHome = navigateToHome,
            onBackClick = onBackClick,
            navigateToUpdateProfile = navigateToUpdateProfile
        )
    }
}
