package com.app.ecitizen.features.advertisement

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable


const val advertisementScreenNavigationRoute = "advertisement_board_route"

fun NavController.navigateToAdvertisement() {
    this.navigate(advertisementScreenNavigationRoute)
}

fun NavGraphBuilder.advertisementScreen(
    onBackClick: () -> Unit,
    navigateToImagePreview: (String) -> Unit,
) {

    composable(route = advertisementScreenNavigationRoute) {
        AdvertisementScreenRoute(
            onBackClick = onBackClick,
            navigateToImagePreview = navigateToImagePreview
        )
    }

}

