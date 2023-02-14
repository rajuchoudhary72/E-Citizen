package com.app.ecitizen.features.service

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.ecitizen.R
import com.app.ecitizen.ui.components.ErrorAndLoadingScreen
import com.app.ecitizen.ui.theme.ECitizenTheme

@Composable
fun ServiceScreenRoute(
    onBackClick: () -> Unit,
    serviceViewModel: ServiceViewModel = hiltViewModel(),
) {
    val uiState: ServiceUiState by serviceViewModel.uiState.collectAsStateWithLifecycle()
    ServiceScreen(
        onBackClick = onBackClick,
        uiState = uiState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceScreen(
    onBackClick: () -> Unit,
    uiState: ServiceUiState,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.services),
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

        val context = LocalContext.current
        when (uiState) {
            is ServiceUiState.Error -> {
                ErrorAndLoadingScreen(error = uiState.appError)
            }

            ServiceUiState.Loading -> {
                ErrorAndLoadingScreen(true)
            }

            is ServiceUiState.Success -> {
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(
                        uiState.services,
                        key = { it.nameRes }
                    ) { service ->
                        ServiceCardItem(
                            service = service,
                            onClickService = {
                                val intent = Intent(Intent.ACTION_VIEW)
                                intent.data = service.url?.toUri()
                                context.startActivity(intent)
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
fun ServiceCardItem(
    service: Service,
    onClickService: () -> Unit
) {
    Card(
        onClick = onClickService,
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .height(140.dp)
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(60.dp),
                painter = painterResource(id = service.imgRes),
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = stringResource(id = service.nameRes),
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    ECitizenTheme {
        ServiceScreen(
            onBackClick = {},
            uiState = ServiceUiState.Success(emptyList()),
        )
    }
}
