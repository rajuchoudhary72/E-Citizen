package com.app.ecitizen.features.noticeBoard.notice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.app.ecitizen.R
import com.app.ecitizen.data.network.dto.NoticeDto
import com.app.ecitizen.ui.components.ErrorAndLoadingScreen
import com.app.ecitizen.ui.theme.ECitizenTheme

@Composable
fun NoticeScreenRoute(
    onBackClick: () -> Unit,
    previewImage: (String) -> Unit,
    noticeViewModel: NoticeViewModel = hiltViewModel(),
) {
    val uiState: NoticeUiState by noticeViewModel.uiState.collectAsStateWithLifecycle()
    NoticeScreen(
        noticeType = noticeViewModel.noticeType,
        uiState = uiState,
        onBackClick = onBackClick,
        viewNotice = previewImage
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoticeScreen(
    noticeType: String,
    uiState: NoticeUiState,
    onBackClick: () -> Unit,
    viewNotice: (String) -> Unit,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = noticeType,
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
            is NoticeUiState.Error -> {
                ErrorAndLoadingScreen(error = uiState.appError)
            }

            NoticeUiState.Loading -> {
                ErrorAndLoadingScreen(isLoading = true)
            }

            is NoticeUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.padding(padding),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(
                        uiState.notices,
                        key = { it.id }
                    ) { notice ->
                        NoticeItem(
                            notice= notice,
                            onClick = viewNotice
                        )
                    }

                }
            }
        }
    }

}

@Composable
fun NoticeItem(
    notice: NoticeDto,
    onClick: (String) -> Unit,
) {
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
                verticalAlignment = Alignment.CenterVertically
            ) {

                AsyncImage(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(MaterialTheme.shapes.small),
                    model = notice.fileUrl(),
                    placeholder = painterResource(id = R.drawable.ic_splash_logo),
                    error = painterResource(id = R.drawable.ic_splash_logo),
                    contentDescription = null
                )

                Column(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .weight(1f)
                ) {
                    Text(
                        modifier = Modifier,
                        text = notice.notificationType ?: "",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        modifier = Modifier,
                        text = notice.createdAt,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                TextButton(
                    modifier = Modifier,
                    onClick = { onClick(notice.fileUrl()) }
                ) {
                    Text(
                        text = stringResource(R.string.view),
                        textDecoration = TextDecoration.Underline
                    )
                }

            }

            if (notice.message.isNullOrEmpty().not())
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    text = notice.message!!,
                    style = MaterialTheme.typography.bodyMedium
                )


        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoticeScreenPreview() {
    ECitizenTheme {
        /*NoticeScreen(
            onBackClick = {}
        )*/
    }
}
