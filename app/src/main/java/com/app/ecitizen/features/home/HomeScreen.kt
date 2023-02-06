package com.app.ecitizen.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.ecitizen.R
import com.app.ecitizen.features.complaint.ComplaintsDialog
import com.app.ecitizen.ui.theme.ECitizenTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay

@Composable
fun HomeScreenRoute(
    navigateToService: (Service) -> Unit,
    navigateToRegisterComplaint: () -> Unit,
    navigateToViewComplaints: () -> Unit,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
) {
    val homeUiState: HomeUiState by homeScreenViewModel.homeUiState.collectAsStateWithLifecycle()

    if (homeScreenViewModel.shouldShowComplaintDialog) {
        ComplaintsDialog(
            onDismiss = { homeScreenViewModel.setShowComplaintDialog(false) },
            navigateToViewComplaints = {
                homeScreenViewModel.setShowComplaintDialog(false)
                navigateToViewComplaints()
            },
            navigateToRegisterComplaint = {
                homeScreenViewModel.setShowComplaintDialog(false)
                navigateToRegisterComplaint()
            }
        )
    }

    HomeScreen(
        homeUiState = homeUiState,
        navigateToService = navigateToService,
        showComplaintDialog = homeScreenViewModel::setShowComplaintDialog
    )
}

@Composable
fun HomeScreen(
    homeUiState: HomeUiState,
    navigateToService: (Service) -> Unit,
    showComplaintDialog: (Boolean) -> Unit
) {
    when (homeUiState) {
        HomeUiState.Loading -> {


        }

        is HomeUiState.Error -> {

        }

        is HomeUiState.Success -> {
            val MAX_SPAN = 2
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(MAX_SPAN),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                item(
                    span = {
                        GridItemSpan(MAX_SPAN)
                    }
                ) {
                    HomeScreenCarousel(homeUiState.bannerImages)
                }

                items(
                    homeUiState.services
                ) { service ->
                    ServicesCard(
                        service,
                        onClickService = {
                            if (service.name == R.string.register_complaints) {
                                showComplaintDialog(true)
                            } else {
                                navigateToService(service)
                            }
                        }
                    )
                }
            }
        }
    }


}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreenCarousel(images: List<String>) {

    val pagerState = rememberPagerState()

    LaunchedEffect(pagerState) {
        delay(3000)
        var newPosition = pagerState.currentPage.plus(1)
        if (newPosition > images.size - 1) newPosition = 0
        pagerState.scrollToPage(newPosition)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            count = images.size,
            state = pagerState
        ) { page: Int ->
            CarouselItem(images[page])
        }
        HorizontalPagerIndicator(
            modifier = Modifier.padding(top = 16.dp), pagerState = pagerState
        )
    }
}

@Composable
fun CarouselItem(image: String) {

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(1.5.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.demo_img),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicesCard(
    service: Service,
    onClickService: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp),
        colors = CardDefaults.cardColors(
            containerColor = service.backgroundColor,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        onClick = onClickService
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
        ) {

            Icon(
                painter = painterResource(id = service.icon),
                contentDescription = null
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = service.name),
                style = MaterialTheme.typography.labelLarge,
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = service.description,
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Light),
            )

        }
    }

}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ECitizenTheme() {
        HomeScreen(
            homeUiState = HomeUiState.Success(
                bannerImages = mutableListOf("", "", "", "", ""),
                services = Service.SERVICES,
            ),
            navigateToService = {},
            showComplaintDialog = {}
        )
    }
}