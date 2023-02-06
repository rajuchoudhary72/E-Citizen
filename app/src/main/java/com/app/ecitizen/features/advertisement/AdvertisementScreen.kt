package com.app.ecitizen.features.advertisement

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.ecitizen.R
import com.app.ecitizen.ui.theme.ECitizenTheme

@Composable
fun AdvertisementScreenRoute(
    onBackClick: () -> Unit,
    advertisementViewModel: AdvertisementViewModel = hiltViewModel(),
) {
    AdvertisementScreen(
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvertisementScreen(
    onBackClick: () -> Unit,
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
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            items(10) {
                AdvertisementCardItem()
            }

        }
    }


}

@Composable
fun AdvertisementCardItem() {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
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

                Image(
                    modifier = Modifier.size(height = 100.dp, width = 80.dp).clip(RoundedCornerShape(10.dp)),
                    painter = painterResource(id = R.drawable.demo_img),
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
                        text = "General Notice",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "A sample video is given below to get an idea about what we are going to do in this article.",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        modifier = Modifier,
                        text = "12 Jan 2023",
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
            onBackClick = {}
        )
    }
}
