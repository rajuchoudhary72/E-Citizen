package com.app.ecitizen.features.download

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable


const val downloadScreenNavigationRoute = "download_route"

fun NavController.navigateToDownload() {
    this.navigate(downloadScreenNavigationRoute)
}

fun NavGraphBuilder.downloadScreen(
    onBackClick: () -> Unit
) {
    composable(route = downloadScreenNavigationRoute) {
        DownloadScreenRoute(
            onBackClick = onBackClick,
        )
    }

}

