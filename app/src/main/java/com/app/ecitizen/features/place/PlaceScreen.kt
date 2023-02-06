package com.app.ecitizen.features.place

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.ecitizen.R
import com.app.ecitizen.features.splash.SplashScreen
import com.app.ecitizen.ui.theme.ECitizenTheme

@Composable
fun PlaceScreenRoute(
    onBackClick: () -> Unit,
    placeViewModel: PlaceViewModel = hiltViewModel(),
) {
    PlaceScreen(
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceScreen(
    onBackClick: () -> Unit,
) {
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.important_places),
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

@Composable
fun DownloadCardItem(
    onClickDownload: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = R.drawable.india_ashoka),
                contentDescription = null
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp),
                text = "Indira Rasoi Meals @ 8 Rs Indira Rasoi Meals @ 8 Rs",
                style = MaterialTheme.typography.labelLarge,
                maxLines = 2,
            )

            IconButton(
                modifier = Modifier,
                onClick = onClickDownload,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_directions_24),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    ECitizenTheme {
        PlaceScreen(
            onBackClick = {},
        )
    }
}
