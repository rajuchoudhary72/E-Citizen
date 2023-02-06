package com.app.ecitizen.features.download

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.ecitizen.R
import com.app.ecitizen.ui.theme.ECitizenTheme

@Composable
fun DownloadScreenRoute(
    onBackClick: () -> Unit,
    downloadViewModel: DownloadViewModel = hiltViewModel(),
) {
    DownloadScreen(
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadScreen(
    onBackClick: () -> Unit,
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(4) {
                DownloadCardItem(
                    onClickDownload = {

                    }
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadCardItem(
    onClickDownload: () -> Unit
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
                text = "Change of land use - 90A",
                style = MaterialTheme.typography.labelLarge,
            )
            Text(
                modifier = Modifier.padding(top = 2.dp),
                text = "A sample video is given below to get an idea about what we are going to do in this article. Note that we are going to implement this project using the Jetpack Compose.",
                style = MaterialTheme.typography.bodyMedium,
            )

            OutlinedButton(
                modifier = Modifier.padding(top = 10.dp).fillMaxWidth(),
                onClick = onClickDownload
            ){
                Text(text = "Download")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    ECitizenTheme {
        DownloadScreen(
            onBackClick = {},
        )
    }
}
