package com.app.ecitizen.features.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable


const val splashScreenNavigationRoute = "splash_route"
fun NavGraphBuilder.splashScreen(
    navigateToLogin: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToServiceProviderComplaint: () -> Unit,
) {
    composable(route = splashScreenNavigationRoute) {
        SplashScreenRoute(
            navigateToLogin = navigateToLogin,
            navigateToHome = navigateToHome,
            navigateToServiceProviderComplaint = navigateToServiceProviderComplaint
        )
    }
}

