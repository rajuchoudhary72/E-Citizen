package com.app.ecitizen.features.complaint.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
fun ViewModelScreenRoute(
    onBackClick: () -> Unit,
    viewComplaintViewModel: ViewComplaintViewModel = hiltViewModel(),
) {
    ViewModelScreen(
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewModelScreen(
    onBackClick: () -> Unit,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.yout_complaints),
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
                ComplaintItem()
            }

        }
    }

}

@Composable
fun ComplaintItem() {

    var isExpanded: Boolean by rememberSaveable { mutableStateOf(false) }

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(percent = 50)),
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
                        modifier = Modifier,
                        text = "12 Jan 2023 10:00 AM",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                IconButton(onClick = {isExpanded = isExpanded.not()}) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24),
                        contentDescription = null
                    )
                }

            }

            AnimatedVisibility(visible = isExpanded) {

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Divider()

                    Card(

                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                        ) {
                            repeat(5) {
                                ComplaintDetailItem(
                                    modifier = Modifier.padding(bottom = 5.dp)
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun ComplaintDetailItem(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            modifier = Modifier.weight(1f),
            text = "Name",
            style = MaterialTheme.typography.labelLarge
        )

        Text(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = ":",
            style = MaterialTheme.typography.labelLarge
        )

        Text(
            modifier = Modifier.weight(1f),
            text = "Hari Singh Kulhari",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NoticeScreenPreview() {
    ECitizenTheme {
        ViewModelScreen(
            onBackClick = {}
        )
    }
}
