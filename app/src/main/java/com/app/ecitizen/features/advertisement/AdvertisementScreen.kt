package com.app.ecitizen.features.advertisement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.app.ecitizen.BuildConfig
import com.app.ecitizen.R
import com.app.ecitizen.data.network.dto.AdvertisementDto
import com.app.ecitizen.model.ScreenEvent
import com.app.ecitizen.ui.components.LoadingWheel
import com.app.ecitizen.ui.theme.ECitizenTheme
import com.app.ecitizen.utils.toScreenEvent

@Composable
fun AdvertisementScreenRoute(
    onBackClick: () -> Unit,
    navigateToImagePreview: (String) -> Unit,
    advertisementViewModel: AdvertisementViewModel = hiltViewModel(),
) {
    val uiState by advertisementViewModel.uiState.collectAsStateWithLifecycle()
    AdvertisementScreen(
        onBackClick = onBackClick,
        uiState = uiState,
        viewAdvertisement = navigateToImagePreview
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvertisementScreen(
    onBackClick: () -> Unit,
    viewAdvertisement: (String) -> Unit,
    uiState: AdvertisementScreenUiState,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.advertisements),
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
            is AdvertisementScreenUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    when (val event = uiState.appError!!.toScreenEvent()) {
                        is ScreenEvent.ShowSnackbar.MessageResId -> {
                            Text(text = stringResource(id = event.resId))
                        }

                        is ScreenEvent.ShowSnackbar.MessageString -> {
                            Text(text = event.value)
                        }

                        else -> {
                            Text(text = stringResource(id = R.string.error_unknown))
                        }
                    }
                }


            }

            AdvertisementScreenUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    contentAlignment = Alignment.Center

                ) {
                    LoadingWheel(contentDesc = stringResource(id = R.string.loading))
                }
            }

            is AdvertisementScreenUiState.Success -> {

                if (uiState.advertisements.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),

                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = stringResource(R.string.no_data_found))
                    }

                } else {
                    LazyColumn(
                        modifier = Modifier.padding(padding),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(uiState.advertisements) { advertisement ->
                            AdvertisementCardItem(advertisement,viewAdvertisement)
                        }
                    }
                }

            }
        }

    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvertisementCardItem(
    advertisement: AdvertisementDto,
    viewAdvertisement: (String) -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = {viewAdvertisement(advertisement.fileUrl())}
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {

                AsyncImage(
                    model = "${BuildConfig.SERVER_URL}${advertisement.basePath}${advertisement.file}",
                    modifier = Modifier
                        .size(height = 100.dp, width = 80.dp)
                        .clip(RoundedCornerShape(10.dp)),

                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(end = 10.dp),
                        text = advertisement.subject ?: "",
                        style = MaterialTheme.typography.titleLarge
                    )

                    if (advertisement.notes.isNullOrEmpty().not()) {
                        Text(
                            text = advertisement.notes ?: "",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Text(
                        modifier = Modifier,
                        text = advertisement.createdAt,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoticeScreenPreview() {
    ECitizenTheme {
        AdvertisementScreen(
            onBackClick = {},
            uiState = AdvertisementScreenUiState.Loading,
            viewAdvertisement = {}
        )
    }
}
