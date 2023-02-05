package com.app.ecitizen.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.app.ecitizen.ui.theme.ECitizenTheme
import com.app.ecitizen.utils.NetworkMonitor
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = !isSystemInDarkTheme()

            // Update the dark content of the system bars to match the theme
            DisposableEffect(systemUiController, useDarkIcons) {
                systemUiController.systemBarsDarkContentEnabled = !useDarkIcons

                systemUiController.setSystemBarsColor(
                    color = Color.Transparent,
                    darkIcons = useDarkIcons
                )

                onDispose {}
            }
            ECitizenTheme {
                ECitizenApp(
                    networkMonitor = networkMonitor
                )
            }
        }
    }
}
