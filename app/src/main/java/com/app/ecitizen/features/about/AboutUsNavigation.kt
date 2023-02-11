package com.app.ecitizen.features.about

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable


const val aboutUsNavigationRoute = "contact_us_route"


fun NavController.navigateToAboutUs(navOptions: NavOptions? = null) {
    this.navigate(aboutUsNavigationRoute, navOptions)
}

fun NavGraphBuilder.aboutUs(onBackClick: () -> Unit) {
    composable(route = aboutUsNavigationRoute) {
        AboutUsRoute(
            onBackClick
        )
    }
}

