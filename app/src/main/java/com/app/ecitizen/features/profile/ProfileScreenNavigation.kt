package com.app.ecitizen.features.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.app.ecitizen.features.home.homeNavigationRoute


const val profileScreenNavigationRoute = "profile_screen_route"

fun NavController.navigateToProfile(navOptions: NavOptions? = null) {
    this.navigate(profileScreenNavigationRoute, navOptions)
}
fun NavGraphBuilder.profileScreen(
    onBackClick: () -> Unit,
) {
    composable(route = profileScreenNavigationRoute) {
        ProfileScreenRoute(onBackClick
        )
    }
}

