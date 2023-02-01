package com.app.ecitizen.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.app.ecitizen.features.auth.loginScreen
import com.app.ecitizen.features.auth.navigateToOtpVerification
import com.app.ecitizen.features.auth.otpVerificationScreen
import com.app.ecitizen.features.home.homeScreen
import com.app.ecitizen.features.home.navigateToHome
import com.app.ecitizen.features.noticeBoard.navigateToNotice
import com.app.ecitizen.features.noticeBoard.navigateToNoticeBoard
import com.app.ecitizen.features.noticeBoard.noticeBoardScreen
import com.app.ecitizen.features.splash.splashScreen
import com.app.ecitizen.features.splash.splashScreenNavigationRoute

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
    closeSplashScreen: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = splashScreenNavigationRoute,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {

        splashScreen(closeSplashScreen = closeSplashScreen)

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
            navigateToService = { service ->
                navController.navigateToNoticeBoard()
            }
        )
        noticeBoardScreen(
            onBackClick = onBackClick,
            navigateToNotice = {
                navController.navigateToNotice()
            }
        )
    }
}
