package com.app.ecitizen.features.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable


const val splashScreenNavigationRoute = "splash_route"
fun NavGraphBuilder.splashScreen(
    closeSplashScreen: () -> Unit
) {
    composable(route = splashScreenNavigationRoute) {
        SplashScreenRoute(
            closeSplashScreen = closeSplashScreen
        )
    }
}

