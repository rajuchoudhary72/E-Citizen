package com.app.ecitizen.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.ecitizen.R

@Composable
fun HomeScreenRoute(
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
) {
    HomeScreen()
}

@Composable
fun HomeScreen() {
    HomeScreenCarousel()
    ServicesGrid()
}

@Composable
fun HomeScreenCarousel() {
    Image(
        modifier = Modifier.fillMaxWidth().height(200.dp),
        painter = painterResource(id = R.drawable.welcome),
        contentDescription = null
    )
}

@Composable
fun ServicesGrid() {
    LazyVerticalGrid(columns = GridCells.Fixed(3)) {
    }
}
