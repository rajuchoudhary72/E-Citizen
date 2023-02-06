package com.app.ecitizen.features.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable


const val homeNavigationRoute = "home_route"


fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(homeNavigationRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(
    navigateToService: (Service) -> Unit,
    navigateToRegisterComplaint: () -> Unit,
    navigateToViewComplaints: () -> Unit
) {
    composable(route = homeNavigationRoute) {
        HomeScreenRoute(
            navigateToService = navigateToService,
            navigateToRegisterComplaint = navigateToRegisterComplaint,
            navigateToViewComplaints = navigateToViewComplaints
        )
    }
}

