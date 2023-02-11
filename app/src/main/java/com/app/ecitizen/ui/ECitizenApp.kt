package com.app.ecitizen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GTranslate
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.app.ecitizen.R
import com.app.ecitizen.features.about.aboutUsNavigationRoute
import com.app.ecitizen.features.commOffice.commOfficeNavigationRoute
import com.app.ecitizen.features.home.homeNavigationRoute
import com.app.ecitizen.features.localization.AppLocaleDialog
import com.app.ecitizen.features.notification.navigateToNotification
import com.app.ecitizen.features.profile.profileScreenNavigationRoute
import com.app.ecitizen.ui.navigation.ECitizenNavHost
import com.app.ecitizen.ui.navigation.TopLevelDestination
import com.app.ecitizen.utils.NetworkMonitor

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ECitizenApp(
    networkMonitor: NetworkMonitor,
    appState: ECitizenAppState = rememberECitizenAppState(networkMonitor = networkMonitor)
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val isOffline by appState.isOffline.collectAsStateWithLifecycle()

    var shouldShowBottomBar by rememberSaveable {
        mutableStateOf(false)
    }
    var shouldShowAppBar by rememberSaveable {
        mutableStateOf(false)
    }

    // If user is not connected to the internet show a snack bar to inform them.
    val notConnectedMessage = stringResource(R.string.not_connected)
    LaunchedEffect(isOffline, appState.currentDestination) {
        if (isOffline) {
            snackbarHostState.showSnackbar(
                message = notConnectedMessage,
                duration = SnackbarDuration.Indefinite,
            )
        }

        shouldShowAppBar =
            appState.navController.currentDestination?.route?.contains(homeNavigationRoute) == true

        shouldShowBottomBar = mutableListOf(
            homeNavigationRoute,
            profileScreenNavigationRoute,
            aboutUsNavigationRoute,
            commOfficeNavigationRoute
        ).any { route -> route == appState.navController.currentDestination?.route }
    }


    if (appState.shouldShowAppLocaleDialog) {
        AppLocaleDialog(
            onDismiss = appState::hideAppLocaleDialog,
        )
    }

    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            if (shouldShowAppBar)
                CenterAlignedTopAppBar(
                    navigationIcon = {
                        Image(
                            modifier = Modifier
                                .size(60.dp)
                                .padding(start = 16.dp),
                            painter = painterResource(id = R.drawable.ic_splash_logo),
                            contentDescription = null
                        )
                    },
                    title = {
                        Text(
                            text = stringResource(R.string.khandela_nagar_palika),
                            style = MaterialTheme.typography.titleMedium.copy(fontFamily = FontFamily.Serif),
                            textAlign = TextAlign.Center
                        )
                    },
                    actions = {

                        IconButton(onClick = appState::showAppLocaleDialog) {
                            Icon(
                                imageVector = Icons.Outlined.GTranslate,
                                contentDescription = null
                            )
                        }

                        IconButton(onClick = {
                            appState.navController.navigateToNotification()
                        }) {

                            Icon(
                                imageVector = Icons.Outlined.Notifications,
                                contentDescription = null
                            )
                        }
                    }
                )
        },
        bottomBar = {
            if (shouldShowBottomBar)
                ECitizenBottomBar(
                    destinations = appState.topLevelDestinations,
                    onNavigateToDestination = appState::navigateToTopLevelDestination,
                    currentDestination = appState.currentDestination
                )
        }
    ) { padding ->
        ECitizenNavHost(
            modifier = Modifier.padding(padding).consumedWindowInsets(padding),
            navController = { appState.navController },
            onBackClick = appState::onBackClick,
            openAppLocaleSettings = appState::showAppLocaleDialog,
            closeSplashScreen = appState::closeSplashScreen,
            snackbarHostState = { snackbarHostState }
        )
    }


}


@Composable
private fun ECitizenBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    BottomAppBar(
        modifier = modifier,
    ) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    val icon = if (selected) {
                        destination.selectedIcon
                    } else {
                        destination.unselectedIcon
                    }

                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                    )

                },
                label = { Text(stringResource(destination.iconTextId)) },
            )
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false
