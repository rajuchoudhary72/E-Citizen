package com.app.ecitizen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.Surface
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.app.ecitizen.R
import com.app.ecitizen.features.home.homeNavigationRoute
import com.app.ecitizen.features.localization.AppLocaleDialog
import com.app.ecitizen.ui.navigation.ECitizenNavHost
import com.app.ecitizen.ui.navigation.TopLevelDestination
import com.app.ecitizen.utils.NetworkMonitor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ECitizenApp(
    networkMonitor: NetworkMonitor,
    appState: ECitizenAppState = rememberECitizenAppState(networkMonitor = networkMonitor)
) {

    Surface {
        val snackbarHostState = remember { SnackbarHostState() }

        val isOffline by appState.isOffline.collectAsStateWithLifecycle()

        var shouldShowBottomBar by rememberSaveable {
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

            shouldShowBottomBar =
                appState.navController.currentDestination?.route?.contains(homeNavigationRoute) == true
        }


        if (appState.shouldShowAppLocaleDialog) {
            AppLocaleDialog(
                onDismiss = { appState.setShowAppLocaleDialog(false) },
            )
        }

        Scaffold(
            modifier = Modifier,
            snackbarHost = { SnackbarHost(snackbarHostState) },
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            topBar = {
                if (shouldShowBottomBar)
                    CenterAlignedTopAppBar(
                        navigationIcon = {
                            Image(
                                modifier = Modifier.size(45.dp).padding(start = 16.dp),
                                painter = painterResource(id = R.drawable.india_ashoka),
                                contentDescription =null
                            )
                        },
                        title = {
                            Text(
                                text = "Khandela Nagar Palika",
                                style = MaterialTheme.typography.titleMedium.copy(fontFamily = FontFamily.Serif)
                            )
                        },
                        actions = {
                            IconButton(onClick = {}) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_notifications_24),
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
                modifier = Modifier.padding(padding),
                navController = appState.navController,
                onBackClick = appState::onBackClick,
                openAppLocaleSettings = { appState.setShowAppLocaleDialog(true) },
                closeSplashScreen = { appState.closeSplashScreen() },
            )
        }
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
