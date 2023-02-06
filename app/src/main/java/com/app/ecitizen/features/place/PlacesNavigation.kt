package com.app.ecitizen.features.place

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable


const val placesScreenNavigationRoute = "places_route"

fun NavController.navigateToPlaces() {
    this.navigate(placesScreenNavigationRoute)
}

fun NavGraphBuilder.placesScreen(
    onBackClick: () -> Unit
) {
    composable(route = placesScreenNavigationRoute) {
        PlaceScreenRoute(
            onBackClick = onBackClick,
        )
    }

}

