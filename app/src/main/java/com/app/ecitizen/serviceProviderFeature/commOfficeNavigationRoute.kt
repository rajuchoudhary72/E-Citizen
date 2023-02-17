package com.app.ecitizen.serviceProviderFeature

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val serviceProviderComplaintScreenNavigationRoute = "service_provider_complaint_screen_route"

fun NavController.navigateToServiceProviderComplaintScreen(navOptions: NavOptions? = null) {
    this.navigate(serviceProviderComplaintScreenNavigationRoute, navOptions)
}

fun NavGraphBuilder.serviceProviderComplaintScreen() {
    composable(route = serviceProviderComplaintScreenNavigationRoute) {
        ServiceProviderComplaintScreenRoute()
    }
}
