package com.app.ecitizen.features.complaint.register

import android.app.Activity
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.app.ecitizen.model.ScreenEvent
import com.app.ecitizen.ui.theme.ECitizenTheme
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.util.FileUriUtils
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun RegisterComplaintRoute(
    onBackClick: () -> Unit,
    registerComplaintViewModel: RegisterComplaintViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val snackbarHostState = rememberScaffoldState().snackbarHostState
    val coroutineScope = rememberCoroutineScope()

    val photoPickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri: Uri = data?.data!!
                    val file = File(FileUriUtils.getRealPath(context, fileUri)!!)
                    registerComplaintViewModel.updateImage(file)
                }

                ImagePicker.RESULT_ERROR -> {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(ImagePicker.getError(data))
                    }
                }
            }
        }




    LaunchedEffect(true) {
        registerComplaintViewModel.updateComplaintType(
            context.resources.getStringArray(R.array.complaint_type).first()
        )


        registerComplaintViewModel
            .screenEvent
            .collectLatest { event ->
                when (event) {
                    is ScreenEvent.Navigate -> {
                        onBackClick()
                    }

                    is ScreenEvent.ShowSnackbar.MessageResId -> {
                        snackbarHostState.showSnackbar(context.getString(event.resId))
                    }

                    is ScreenEvent.ShowSnackbar.MessageString -> {
                        snackbarHostState.showSnackbar(event.value)
                    }
                }
            }
    }

    var isFormFilled by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(
        registerComplaintViewModel.wardNumber,
        registerComplaintViewModel.note,
        registerComplaintViewModel.streetName,
        registerComplaintViewModel.colonyName,
        registerComplaintViewModel.image,
    ) {
        isFormFilled = registerComplaintViewModel.wardNumber.isNotEmpty() &&
                registerComplaintViewModel.note.isNotEmpty() &&
                registerComplaintViewModel.streetName.isNotEmpty() &&
                registerComplaintViewModel.colonyName.isNotEmpty() &&
                registerComplaintViewModel.image != null
    }

    RegisterComplaintScreen(
        onBackClick = onBackClick,
        wardNumber = registerComplaintViewModel.wardNumber,
        houseNumber = registerComplaintViewModel.houseNumber,
        colonyName = registerComplaintViewModel.colonyName,
        streetName = registerComplaintViewModel.streetName,
        complaintType = registerComplaintViewModel.complaintType,
        note = registerComplaintViewModel.note,
        image = registerComplaintViewModel.image,
        updateWardNumber = registerComplaintViewModel::updateWardNumber,
        updateHouseNumber = registerComplaintViewModel::updateHouseNumber,
        updateColonyName = registerComplaintViewModel::updateColonyName,
        updateStreetName = registerComplaintViewModel::updateStreetName,
        updateNote = registerComplaintViewModel::updateNote,
        updateComplaintType = registerComplaintViewModel::updateComplaintType,
        pickPhoto = {
            ImagePicker
                .with(context as Activity)
                .compress(500)
                .createIntent { intent ->
                    photoPickerLauncher.launch(intent)
                }

        },
        isFormFilled = { isFormFilled },
        isLoading = { registerComplaintViewModel.isLoading },
        registerComplaint = registerComplaintViewModel::registerComplaint

    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun RegisterComplaintScreen(
    onBackClick: () -> Unit,
    wardNumber: String,
    complaintType: String,
    houseNumber: String,
    colonyName: String,
    streetName: String,
    note: String,
    image: File?,
    updateWardNumber: (String) -> Unit,
    updateComplaintType: (String) -> Unit,
    updateHouseNumber: (String) -> Unit,
    updateColonyName: (String) -> Unit,
    updateStreetName: (String) -> Unit,
    updateNote: (String) -> Unit,
    pickPhoto: () -> Unit,
    registerComplaint: () -> Unit,
    isFormFilled: () -> Boolean,
    isLoading: () -> Boolean,
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

                ComplaintTypeTextField(complaintType, updateComplaintType)

                Text(
                    modifier = Modifier.padding(top = 8.dp),
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
                            modifier = Modifier
                                .fillMaxSize(),
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

                if (isLoading()) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(top = 40.dp)
                                .height(50.dp),

                            )
                    }

                } else {
                    Button(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 30.dp),
                        onClick = registerComplaint,
                        enabled = isFormFilled()
                    ) {
                        Text(text = stringResource(id = R.string.submit))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComplaintTypeTextField(complaintType: String, updateComplaintType: (String) -> Unit) {
    val context = LocalContext.current
    val options = context.resources.getStringArray(R.array.complaint_type)

    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth(),
    ) {

        Column {
            Text(
                modifier = Modifier,
                text = stringResource(R.string.complaint_type),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
                    .clickable { expanded = expanded.not() },
                value = complaintType,
                placeholder = { Text(text = stringResource(id = R.string.complaint_type)) },
                onValueChange = {},
                readOnly = true,
                enabled = false,
                trailingIcon = {
                    Icon(
                        imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                    disabledPlaceholderColor = MaterialTheme.colorScheme.onSurface,
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface,
                )
            )
        }


        DropdownMenu(
            modifier = Modifier,
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    text = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = selectionOption
                        )
                    },
                    onClick = {
                        updateComplaintType(selectionOption)
                        expanded = false
                    },
                    contentPadding = PaddingValues(10.dp),
                )
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
            complaintType = "",
            houseNumber = "",
            colonyName = "",
            streetName = "",
            note = "",
            image = null,
            updateWardNumber = {},
            updateComplaintType = {},
            updateHouseNumber = {},
            updateColonyName = {},
            updateStreetName = {},
            updateNote = {},
            pickPhoto = {},
            isFormFilled = { false },
            registerComplaint = {},
            isLoading = { false }
        )
    }
}
