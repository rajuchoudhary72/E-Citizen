package com.app.ecitizen.features.contact.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.app.ecitizen.R
import com.app.ecitizen.data.network.dto.AboutUsDto
import com.app.ecitizen.data.network.dto.PhoneBookDto
import com.app.ecitizen.ui.components.ErrorAndLoadingScreen
import com.app.ecitizen.ui.theme.ECitizenTheme
import com.app.ecitizen.ui.theme.Teal90


@Composable
fun ContactScreenRoute(
    onBackClick: () -> Unit,
    contactViewModel: ContactViewModel = hiltViewModel(),
) {

    val uiState by contactViewModel.uiState.collectAsStateWithLifecycle()

    PhoneBookScreen(
        title = contactViewModel.type,
        uiState = uiState,
        onBackClick = onBackClick,
        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneBookScreen(
    onBackClick: () -> Unit,
    title: String,
    uiState: ContactUiState,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
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
            is ContactUiState.Error -> {
                ErrorAndLoadingScreen(error = uiState.appError)
            }

            ContactUiState.Loading -> {
                ErrorAndLoadingScreen(true)
            }

            is ContactUiState.Success -> {

                val context = LocalContext.current
                LazyColumn(
                    modifier = Modifier.padding(padding),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(
                        uiState.phones,
                        key = { it.id }
                    ) { contact ->
                        ContactCardItem(
                            contact = contact,
                            onClickCall = {
                                val intent = Intent(Intent.ACTION_DIAL)
                                intent.data = Uri.parse("tel:${contact.phoneNo}")
                                context.startActivity(intent)
                            },
                            onClickMessage = {
                                val sendIntent = Intent(Intent.ACTION_VIEW)
                                sendIntent.data = Uri.parse("sms:${contact.phoneNo}")
                                context.startActivity(sendIntent)
                            },
                        )
                    }

                }
            }
        }
    }


}

@Composable
fun ContactCardItem(
    contact: AboutUsDto,
    onClickCall: () -> Unit,
    onClickMessage: () -> Unit
) {
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

                AsyncImage(
                    model = contact.fileUrl(),
                    modifier = Modifier
                        .clip( shape = RoundedCornerShape(10.dp))
                        .background(Color.LightGray)
                        .height(80.dp)
                        .width(70.dp),
                    placeholder = painterResource(id = R.drawable.outline_person_24),
                    error = painterResource(id = R.drawable.outline_person_24),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
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
                        text = (contact.name + "(${contact.postType})"),
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        modifier = Modifier,
                        text = contact.designation?:"",
                        style = MaterialTheme.typography.bodyMedium
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
        PhoneBookScreen(
            onBackClick = {},
            title = "Phone Book",
            uiState = ContactUiState.Success(mutableListOf(AboutUsDto(id = "12")))
        )
    }
}
