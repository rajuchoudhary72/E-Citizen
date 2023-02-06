package com.app.ecitizen.features.telephone.phoneBook

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.ecitizen.R
import com.app.ecitizen.ui.theme.ECitizenTheme
import com.app.ecitizen.ui.theme.Teal90

@Composable
fun PhoneBookScreenRoute(
    onBackClick: () -> Unit,
    phoneBookViewModel: PhoneBookViewModel = hiltViewModel(),
) {
    PhoneBookScreen(
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneBookScreen(
    onBackClick: () -> Unit,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.phone_book),
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
                ContactCardItem()
            }

        }
    }


}

@Composable
fun ContactCardItem() {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
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
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .background(color = Teal90, shape = RoundedCornerShape(50)),
                    painter = painterResource(id = R.drawable.outline_person_24),
                    contentDescription = null,
                    contentScale = ContentScale.Inside
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier
                            .padding(end = 10.dp),
                        text = "General Notice",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        modifier = Modifier,
                        text = "+91 9898987977",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Row {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_chat_24),
                            contentDescription = null
                        )
                    }

                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_local_phone_24),
                            contentDescription = null
                        )
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoticeScreenPreview() {
    ECitizenTheme {
        PhoneBookScreen(
            onBackClick = {}
        )
    }
}
