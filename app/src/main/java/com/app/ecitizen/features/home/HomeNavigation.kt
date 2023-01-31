package com.app.ecitizen.features.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable


const val homeNavigationRoute = "home_route"


fun NavController.navigateToHome() {
    this.navigate(homeNavigationRoute)
}

fun NavGraphBuilder.homeScreen(
    openAppLocaleSettings: () -> Unit
) {
    composable(route = homeNavigationRoute) {
        HomeScreenRoute()
    }
}

