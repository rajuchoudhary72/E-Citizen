package com.app.ecitizen.features.complaint.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.app.ecitizen.model.ScreenEvent
import com.app.ecitizen.ui.components.ErrorAndLoadingScreen
import com.app.ecitizen.ui.theme.ECitizenTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ViewModelScreenRoute(
    onBackClick: () -> Unit,
    navigateToImagePreview: (String) -> Unit,
    viewComplaintViewModel: ViewComplaintViewModel = hiltViewModel(),
) {
    val uiState by viewComplaintViewModel.uiState.collectAsStateWithLifecycle()

    val loadingComplaintId by viewComplaintViewModel.loadingComplaintId.collectAsState(initial = null)

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(true) {

        viewComplaintViewModel
            .screenEvent
            .collectLatest { event ->
                when (event) {
                    is ScreenEvent.ShowSnackbar.MessageString -> {
                        scaffoldState.snackbarHostState.showSnackbar(event.value)
                    }
                    else ->{}
                }
            }
    }

    ViewModelScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        navigateToImagePreview = navigateToImagePreview,
        scaffoldState = scaffoldState,
        loadingComplaintId = loadingComplaintId,
        closeComplaint = viewComplaintViewModel::closeComplaint
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewModelScreen(
    onBackClick: () -> Unit,
    uiState: ViewComplaintUiState,
    navigateToImagePreview: (String) -> Unit,
    scaffoldState: ScaffoldState,
    loadingComplaintId: String?,
    closeComplaint: (Complaint) -> Unit,
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
        scaffoldState = scaffoldState
    ) { padding ->

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
                        modifier = Modifier.padding(padding),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(uiState.complaints, key = { it.id }) { complaint ->
                            ComplaintItem(
                                complaint = complaint,
                                viewPhoto = {navigateToImagePreview(complaint.fileUrl())},
                                loadingComplaintId = loadingComplaintId,
                                closeComplaint = { closeComplaint(complaint) }
                            )
                        }

                    }
                }
            }
        }

    }

}

@Composable
fun ComplaintItem(
    complaint: Complaint,
    viewPhoto: () -> Unit,
    closeComplaint: () -> Unit,
    loadingComplaintId: String?
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
                    Text(
                        modifier = Modifier
                            .padding(end = 10.dp),
                        text = complaint.complainType,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
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
                                description = context.getString(R.string.ward_number) + "-" + complaint.wardNo + ", "+context.getString(R.string.house_number) + "-" + complaint.houseNo+", "+complaint.streetName+", "+complaint.colony
                            )

                            ComplaintDetailItem(
                                modifier = Modifier.padding(bottom = 5.dp),
                                title = R.string.mobile_number,
                                description = complaint.phone
                            )

                            ComplaintDetailItem(
                                modifier = Modifier.padding(bottom = 5.dp),
                                title = R.string.id,
                                description = complaint.phone
                            )

                            ComplaintDetailItem(
                                modifier = Modifier.padding(bottom = 5.dp),
                                title = R.string.note,
                                description = complaint.notes
                            )

                            if(complaint.status == "1"){
                                if(loadingComplaintId == complaint.id){
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .padding(vertical = 10.dp)
                                            .height(50.dp),
                                        )
                                }else{
                                    OutlinedButton(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 5.dp),
                                        onClick =closeComplaint
                                    ) {
                                        Text(text = stringResource(id = R.string.close_complaint))
                                    }
                                }

                            }else{
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

        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = title),
            style = MaterialTheme.typography.labelLarge
        )

        Text(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = ":",
            style = MaterialTheme.typography.labelLarge
        )

        Text(
            modifier = Modifier.weight(1f),
            text = description,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NoticeScreenPreview() {
    ECitizenTheme {
        ViewModelScreen(
            onBackClick = {},
            uiState = ViewComplaintUiState.Loading,
            navigateToImagePreview = {},
            scaffoldState = rememberScaffoldState(),
            loadingComplaintId = null,
            closeComplaint = {}
        )
    }
}
