package com.app.ecitizen.serviceProviderFeature

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.app.ecitizen.R
import com.app.ecitizen.data.network.dto.Complaint
import com.app.ecitizen.ui.components.ErrorAndLoadingScreen
import com.app.ecitizen.ui.theme.ECitizenTheme
import kotlinx.coroutines.launch

@Composable
fun ServiceProviderComplaintScreenRoute(
    serviceViewModel: ServiceProviderComplaintViewModel = hiltViewModel()
) {
    val uiState by serviceViewModel.uiState.collectAsStateWithLifecycle()

    ServiceProviderComplaintScreen(
        uiState = uiState,
        updateStatus = serviceViewModel::updateStats
    )
}

@Composable
fun ServiceProviderComplaintScreen(
    uiState: ViewComplaintUiState,
    updateStatus: (String) -> Unit
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val complaintPages = context.resources.getStringArray(R.array.service_provider_complaints)
    var selectedTab by remember {
        mutableStateOf(0)
    }


    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            TabRow(
                selectedTabIndex = selectedTab,
                backgroundColor = MaterialTheme.colorScheme.surface,
            ) {
                complaintPages.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = selectedTab == index,
                        onClick = {
                            coroutineScope.launch {
                                selectedTab = index
                                updateStatus(index.plus(1).toString())
                            }
                        },
                    )
                }
            }
            when (uiState) {
                is ViewComplaintUiState.Error -> {
                    ErrorAndLoadingScreen(error = uiState.appError)
                }

                ViewComplaintUiState.Loading -> {
                    ErrorAndLoadingScreen(true)
                }

                is ViewComplaintUiState.Success -> {

                    if (uiState.complaints.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp),
                            contentAlignment = Alignment.Center
                        ) {

                            Text(text = stringResource(id = R.string.no_item_found))

                        }
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(
                                horizontal = 16.dp,
                                vertical = 10.dp
                            ),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {


                            items(uiState.complaints, key = { it.id }) { complaint ->

                                ComplaintCard(
                                    complaint = complaint,
                                    viewPhoto = {}
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
fun ComplaintCard(
    complaint: Complaint,
    viewPhoto: () -> Unit,
) {

    var isExpanded: Boolean by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current
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

                AsyncImage(
                    model = complaint.fileUrl(),
                    modifier = Modifier
                        .clickable { viewPhoto() }
                        .size(50.dp)
                        .clip(RoundedCornerShape(percent = 50)),
                    placeholder = painterResource(id = R.drawable.demo_img),
                    error = painterResource(id = R.drawable.demo_img),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp)
                ) {
                    androidx.compose.material3.Text(
                        modifier = Modifier
                            .padding(end = 10.dp),
                        text = complaint.complainType,
                        style = MaterialTheme.typography.titleMedium
                    )

                    androidx.compose.material3.Text(
                        modifier = Modifier,
                        text = complaint.createdAt,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                IconButton(onClick = { isExpanded = isExpanded.not() }) {
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
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            ComplaintDetailItem(
                                modifier = Modifier.padding(bottom = 5.dp),
                                title = R.string.full_name,
                                description = complaint.fname!!
                            )

                            ComplaintDetailItem(
                                modifier = Modifier.padding(bottom = 5.dp),
                                title = R.string.address,
                                description = context.getString(R.string.ward_number) + "-" + complaint.wardNo + ", " + context.getString(
                                    R.string.house_number
                                ) + "-" + complaint.houseNo + ", " + complaint.streetName + ", " + complaint.colony
                            )

                            ComplaintDetailItem(
                                modifier = Modifier.padding(bottom = 5.dp),
                                title = R.string.mobile_number,
                                description = complaint.phone
                            )

                            ComplaintDetailItem(
                                modifier = Modifier.padding(bottom = 5.dp),
                                title = R.string.id,
                                description = complaint.complainId
                            )

                            ComplaintDetailItem(
                                modifier = Modifier.padding(bottom = 5.dp),
                                title = R.string.note,
                                description = complaint.notes
                            )

                            ComplaintDetailItem(
                                modifier = Modifier.padding(bottom = 5.dp),
                                title = R.string.status,
                                description = context.getString(R.string.closed)
                            )
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun ComplaintDetailItem(
    modifier: Modifier = Modifier,
    title: Int,
    description: String
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {

        androidx.compose.material3.Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = title),
            style = MaterialTheme.typography.labelLarge
        )

        androidx.compose.material3.Text(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = ":",
            style = MaterialTheme.typography.labelLarge
        )

        androidx.compose.material3.Text(
            modifier = Modifier.weight(1f),
            text = description,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
fun ServiceProviderComplaintScreenPreview() {
    ECitizenTheme {
        //ServiceProviderComplaintScreen(U)
    }
}