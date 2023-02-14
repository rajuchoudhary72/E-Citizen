package com.app.ecitizen.features.place

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.ecitizen.R
import com.app.ecitizen.ui.components.ErrorAndLoadingScreen
import com.app.ecitizen.ui.theme.ECitizenTheme
import kotlinx.coroutines.flow.collectLatest
import java.util.Locale


@Composable
fun PlaceScreenRoute(
    onBackClick: () -> Unit,
    placeViewModel: PlaceViewModel = hiltViewModel(),
) {
    val uiState by placeViewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(true) {
        placeViewModel
            .visitPlaceDto
            .collectLatest { place ->
                val uri: String =
                    java.lang.String.format(
                        Locale.ENGLISH,
                        "geo:%s,%s",
                        place.impPlaceLat,
                        place.impPlaceLong
                    )
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                context.startActivity(intent)
            }
    }
    PlaceScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        visitPlace = placeViewModel::visitPlace
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceScreen(
    onBackClick: () -> Unit,
    visitPlace: (ImportantPlace) -> Unit,
    uiState: ImportantPlaceUiState,
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
        when (uiState) {
            ImportantPlaceUiState.Loading -> {
                ErrorAndLoadingScreen(true)
            }

            is ImportantPlaceUiState.Error -> {}
            is ImportantPlaceUiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(
                        uiState.places,
                        key = { it.name }
                    ) { place ->
                        ImportantPlaceItem(
                            place = place,
                            visitPlace = {
                                visitPlace(place)
                            }
                        )
                    }
                }
            }
        }

    }

}

@Composable
fun ImportantPlaceItem(
    visitPlace: () -> Unit,
    place: ImportantPlace
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
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(50)
                    )
                    .size(40.dp),
                painter = painterResource(place.icon),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp),
                text = stringResource(id = place.name),
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
            )

            IconButton(
                modifier = Modifier,
                onClick = visitPlace,
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
            uiState = ImportantPlaceUiState.Success(ImportantPlace.IMPORTANT_PLACES),
            visitPlace = {}
        )
    }
}
