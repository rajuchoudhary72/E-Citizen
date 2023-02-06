package com.app.ecitizen.features.service

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable


const val serviceScreenNavigationRoute = "service_route"

fun NavController.navigateToService() {
    this.navigate(serviceScreenNavigationRoute)
}

fun NavGraphBuilder.serviceScreen(
    onBackClick: () -> Unit
) {
    composable(route = serviceScreenNavigationRoute) {
        ServiceScreenRoute(
            onBackClick = onBackClick,
        )
    }

}

