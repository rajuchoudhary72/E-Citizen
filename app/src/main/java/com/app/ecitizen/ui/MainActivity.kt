package com.app.ecitizen.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.app.ecitizen.data.datastore.ECitizenPreferencesDataStore
import com.app.ecitizen.ui.theme.ECitizenTheme
import com.app.ecitizen.utils.NetworkMonitor
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    private val viewModel: MainActivityViewModel by viewModels()

    @Inject
    lateinit var preferencesDataStore: ECitizenPreferencesDataStore

    private val logoutBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            when (p1?.extras?.get("action")) {
                ACTION_LOGOUT -> {
                   logout()
                }
            }
        }
    }


    fun logout(){
        lifecycleScope.launch {
            preferencesDataStore.clearPreferences()
            finishAffinity()
            startActivity(Intent(this@MainActivity, MainActivity::class.java))
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        registerLogoutBroadcastReceiver()

        var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState.Loading)

        // Update the uiState
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .onEach {
                        uiState = it
                    }
                    .collect()
            }
        }

        splashScreen.setKeepOnScreenCondition {
            when (uiState) {
                MainActivityUiState.Loading -> true
                is MainActivityUiState.Success -> false
                else -> {
                    false
                }
            }
        }


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
                    networkMonitor = networkMonitor,
                    logout = {logout()}
                )
            }
        }
    }

    override fun onStop() {
        super.onStop()
        unRegisterLogoutBroadcastReceiver()
    }
    private fun registerLogoutBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(logoutBroadcastReceiver, IntentFilter(APP_LOCAL_BROADCAST))
    }

    private fun unRegisterLogoutBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(logoutBroadcastReceiver)
    }

    companion object {
        const val APP_LOCAL_BROADCAST = "app_local_broadcast"
        const val ACTION_LOGOUT = "logout"
    }
}
