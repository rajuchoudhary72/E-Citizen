package com.app.ecitizen.features.download

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.ecitizen.R
import com.app.ecitizen.data.network.dto.DownloadDto
import com.app.ecitizen.ui.components.ErrorAndLoadingScreen
import com.app.ecitizen.ui.theme.ECitizenTheme
import com.app.ecitizen.utils.downloadFile

@Composable
fun DownloadScreenRoute(
    onBackClick: () -> Unit,
    downloadViewModel: DownloadViewModel = hiltViewModel(),
) {
    val uiState: DownloadUiState by downloadViewModel.uiState.collectAsStateWithLifecycle()
    DownloadScreen(
        onBackClick = onBackClick,
        uiState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadScreen(
    onBackClick: () -> Unit,
    uiState: DownloadUiState,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.downloads),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = null
                        )
                    }
                }
            )
        },
    ) { padding ->

        when (uiState) {
            is DownloadUiState.Error -> {
                ErrorAndLoadingScreen(error = uiState.appError)
            }

            DownloadUiState.Loading -> {
                ErrorAndLoadingScreen(true)
            }

            is DownloadUiState.Success -> {

                val context = LocalContext.current
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {

                    items(uiState.downloads, key = { it.id }) { download ->
                        DownloadCardItem(
                            download = download,
                            onClickDownload = {
                                context.downloadFile(
                                    url = download.fileUrl(),
                                    fileName = download.subject ?: "File Download",
                                    desc = ""
                                )
                            }
                        )
                    }
                }
            }
        }


    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadCardItem(
    onClickDownload: () -> Unit,
    download: DownloadDto
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
        ) {
            Text(
                text = download.subject ?: "",
                style = MaterialTheme.typography.titleMedium,
            )

            OutlinedButton(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                onClick = onClickDownload
            ) {
                Text(text = stringResource(R.string.download))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    ECitizenTheme {
        /* DownloadScreen(
             onBackClick = {},
             uiState = uiState,
         )*/
    }
}
