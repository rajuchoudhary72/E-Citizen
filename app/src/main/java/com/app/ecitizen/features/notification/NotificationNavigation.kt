package com.app.ecitizen.features.notification

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable


const val notificationScreenNavigationRoute = "notification_route"

fun NavController.navigateToNotification() {
    this.navigate(notificationScreenNavigationRoute)
}

fun NavGraphBuilder.notificationScreen(
    onBackClick: () -> Unit,
) {

    composable(route = notificationScreenNavigationRoute) {
        NotificationScreenRoute(onBackClick = onBackClick)
    }

}

