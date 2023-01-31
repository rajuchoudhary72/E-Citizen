package com.app.ecitizen.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.app.ecitizen.features.auth.loginNavigationRoute
import com.app.ecitizen.features.auth.loginScreen
import com.app.ecitizen.features.auth.navigateToOtpVerification
import com.app.ecitizen.features.auth.otpVerificationScreen
import com.app.ecitizen.features.home.homeScreen
import com.app.ecitizen.features.home.navigateToHome

/**
 * Top-level navigation graph. Navigation is organized as explained at
 * https://d.android.com/jetpack/compose/nav-adaptive
 *
 * The navigation graph defined in this file defines the different top level routes. Navigation
 * within each route is handled using state and Back Handlers.
 */
@Composable
fun ECitizenNavHost(
    navController: NavHostController,
    onBackClick: () -> Unit,
    openAppLocaleSettings: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = loginNavigationRoute,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        loginScreen(
            navigateToVerifyOtp = { mobileNumber ->
                navController.navigateToOtpVerification(mobileNumber)
            },
            openAppLocaleSettings = openAppLocaleSettings
        )
        otpVerificationScreen(
            navigateToHome = {
                navController.navigateToHome()
            },
            onBackClick = onBackClick
        )

        homeScreen(
            openAppLocaleSettings = openAppLocaleSettings
        )
    }
}
