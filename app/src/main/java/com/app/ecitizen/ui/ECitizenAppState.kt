package com.app.ecitizen.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.app.ecitizen.features.about.aboutUsNavigationRoute
import com.app.ecitizen.features.about.navigateToAboutUs
import com.app.ecitizen.features.auth.navigateToLoginScreen
import com.app.ecitizen.features.commOffice.commOfficeNavigationRoute
import com.app.ecitizen.features.commOffice.navigateToCommOffice
import com.app.ecitizen.features.home.homeNavigationRoute
import com.app.ecitizen.features.home.navigateToHome
import com.app.ecitizen.features.profile.navigateToProfile
import com.app.ecitizen.features.profile.profileScreenNavigationRoute
import com.app.ecitizen.features.splash.splashScreenNavigationRoute
import com.app.ecitizen.ui.navigation.TopLevelDestination
import com.app.ecitizen.utils.NetworkMonitor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberECitizenAppState(
    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): ECitizenAppState {
    return remember(navController, coroutineScope, networkMonitor) {
        ECitizenAppState(navController, coroutineScope, networkMonitor)
    }
}

@Stable
class ECitizenAppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
    networkMonitor: NetworkMonitor,
) {

    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    var shouldShowAppLocaleDialog by mutableStateOf(false)
        private set

    val isOffline = networkMonitor.isOnline.map(Boolean::not).stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = false,
    )

    val shouldShowBottomBar: Boolean
        @Composable get() = mutableListOf(
            homeNavigationRoute,
            profileScreenNavigationRoute,
            aboutUsNavigationRoute,
            commOfficeNavigationRoute
        ).any { route -> route == navController.currentDestination?.route }


    val shouldShowAppBar: Boolean
        @Composable get() = mutableListOf(
            homeNavigationRoute
        ).any { route -> route == navController.currentDestination?.route }


    /**
     * Map of top level destinations to be used in the TopBar, BottomBar and NavRail. The key is the
     * route.
     */
    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()

    /**
     * UI logic for navigating to a top level destination in the app. Top level destinations have
     * only one copy of the destination of the back stack, and save and restore state whenever you
     * navigate to and from it.
     *
     * @param topLevelDestination: The destination the app needs to navigate to.
     */
    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {

        val topLevelNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }

        when (topLevelDestination) {
            TopLevelDestination.HOME -> {
                navController.navigateToHome(topLevelNavOptions)
            }
            TopLevelDestination.COMMISSIONER_OFFICE -> {
                navController.navigateToCommOffice(topLevelNavOptions)
            }
            TopLevelDestination.CONTACT_US -> {
                navController.navigateToAboutUs(topLevelNavOptions)
            }
            TopLevelDestination.PROFILE -> {
                navController.navigateToProfile(topLevelNavOptions)
            }
        }

    }


    fun onBackClick() {
        navController.popBackStack()
    }

    fun showAppLocaleDialog() {
        shouldShowAppLocaleDialog = true
    }

    fun hideAppLocaleDialog() {
        shouldShowAppLocaleDialog = false
    }

    fun closeSplashScreen() {
        val navOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(splashScreenNavigationRoute) {
                saveState = true
                inclusive = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
        // navController.navigateToHome(navOptions)
        navController.navigateToLoginScreen(navOptions)
    }
}

