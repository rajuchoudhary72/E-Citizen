package com.app.ecitizen.features.localization

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.ecitizen.R
import com.app.ecitizen.model.AppLocale
import com.app.ecitizen.ui.theme.ECitizenTheme

@Composable
fun AppLocaleDialog(
    onDismiss: () -> Unit,
    viewModel: AppLocaleViewModel = hiltViewModel(),
) {
    val appLocaleUiState by viewModel.appLocaleUiState.collectAsStateWithLifecycle()

    AppLocaleDialog(
        appLocaleUiState = appLocaleUiState,
        onDismiss = onDismiss
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppLocaleDialog(
    appLocaleUiState: AppLocaleUiState,
    onDismiss: () -> Unit,
) {
    val configuration = LocalConfiguration.current

    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.widthIn(max = configuration.screenWidthDp.dp - 80.dp),
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = stringResource(R.string.my_language),
                style = MaterialTheme.typography.titleLarge,
            )
        },
        text = {
            when (appLocaleUiState) {
                AppLocaleUiState.Loading -> {}
                is AppLocaleUiState.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),

                        ) {
                        items(appLocaleUiState.appLocale) { appLocale ->
                            AppLocaleGridItem(
                                appLocale = appLocale,
                                isSelected = appLocaleUiState.selectedAppLocale == appLocale,
                                chooseLocale = {}
                            )
                        }
                    }
                }
            }

        },
        confirmButton = { }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppLocaleGridItem(
    appLocale: AppLocale,
    isSelected: Boolean,
    chooseLocale: () -> Unit
) {
    Card(
        onClick = chooseLocale,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = appLocale.flag),
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(id = appLocale.nameRes),
                style = MaterialTheme.typography.titleMedium.copy(fontFamily = FontFamily.Serif)
            )
        }

    }
}

@Preview
@Composable
fun AppLocaleDialogPreview() {
    ECitizenTheme {
        AppLocaleDialog(
            appLocaleUiState = AppLocaleUiState.Success(
                appLocale = AppLocale.values().toList(),
                selectedAppLocale = AppLocale.HINDI
            ),
            onDismiss = {}
        )
    }
}
