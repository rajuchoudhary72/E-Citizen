package com.app.ecitizen.features.complaint.register

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.app.ecitizen.R
import com.app.ecitizen.ui.theme.ECitizenTheme

@Composable
fun RegisterComplaintRoute(
    onBackClick: () -> Unit,
    registerComplaintViewModel: RegisterComplaintViewModel = hiltViewModel(),
) {
    val photoPickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let { registerComplaintViewModel.updateImage(it) }
        }

    RegisterComplaintScreen(
        onBackClick = onBackClick,
        wardNumber = registerComplaintViewModel.wardNumber,
        houseNumber = registerComplaintViewModel.houseNumber,
        colonyName = registerComplaintViewModel.colonyName,
        streetName = registerComplaintViewModel.streetName,
        note = registerComplaintViewModel.note,
        image = registerComplaintViewModel.image,
        updateWardNumber = registerComplaintViewModel::updateWardNumber,
        updateHouseNumber = registerComplaintViewModel::updateHouseNumber,
        updateColonyName = registerComplaintViewModel::updateColonyName,
        updateStreetName = registerComplaintViewModel::updateStreetName,
        updateNote = registerComplaintViewModel::updateNote,
        pickPhoto = {
            photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterComplaintScreen(
    onBackClick: () -> Unit,
    wardNumber: String,
    houseNumber: String,
    colonyName: String,
    streetName: String,
    note: String,
    image: Uri?,
    updateWardNumber: (String) -> Unit,
    updateHouseNumber: (String) -> Unit,
    updateColonyName: (String) -> Unit,
    updateStreetName: (String) -> Unit,
    updateNote: (String) -> Unit,
    pickPhoto: () -> Unit,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.register_complaints),
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
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),

            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {

            item {
                Text(
                    text = stringResource(R.string.ward_number),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    placeholder = { Text(text = stringResource(R.string.enter)) },
                    value = wardNumber,
                    onValueChange = updateWardNumber,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                )

                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = stringResource(R.string.house_number),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    placeholder = { Text(text = stringResource(R.string.enter)) },
                    value = houseNumber,
                    onValueChange = updateHouseNumber,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = stringResource(R.string.colony_name),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    placeholder = { Text(text = stringResource(R.string.enter)) },
                    value = colonyName,
                    onValueChange = updateColonyName,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = stringResource(R.string.street_name),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    placeholder = { Text(text = stringResource(R.string.enter)) },
                    value = streetName,
                    onValueChange = updateStreetName,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = stringResource(R.string.note),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(top = 5.dp),
                    placeholder = { Text(text = stringResource(R.string.note_placeholder)) },
                    value = note,
                    onValueChange = updateNote,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Start),

                    )

                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = stringResource(R.string.photo),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
                )

                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(top = 5.dp),
                    onClick = pickPhoto,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = MaterialTheme.shapes.extraSmall
                ) {
                    if (image != null) {
                       AsyncImage(
                           model = image,
                           contentDescription = null,
                           contentScale = ContentScale.Crop
                       )
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.outline_add_a_photo_24),
                                contentDescription = null
                            )
                            Text(
                                modifier = Modifier.padding(top = 8.dp),
                                text = stringResource(R.string.select_photo),
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
                            )
                        }
                    }
                }

                Button(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 30.dp),
                    onClick = {}
                ) {
                    Text(text = stringResource(id = R.string.submit))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    ECitizenTheme {
        RegisterComplaintScreen(
            onBackClick = {},
            wardNumber = "",
            houseNumber = "",
            colonyName = "",
            streetName = "",
            note = "",
            image = null,
            {}, {}, {}, {}, {}, {},
        )
    }
}
