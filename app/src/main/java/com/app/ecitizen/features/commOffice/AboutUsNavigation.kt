package com.app.ecitizen.features.commOffice

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable


const val commOfficeNavigationRoute = "commissioner_office_route"


fun NavController.navigateToCommOffice(navOptions: NavOptions? = null) {
    this.navigate(commOfficeNavigationRoute, navOptions)
}

fun NavGraphBuilder.commOffice(onBackClick: () -> Unit) {
    composable(route = commOfficeNavigationRoute) {
        CommOfficeRoute(
            onBackClick
        )
    }
}

