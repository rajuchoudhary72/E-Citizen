package com.app.ecitizen.features.profile

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.ecitizen.R
import com.app.ecitizen.data.network.dto.UserDto
import com.app.ecitizen.features.home.homeNavigationRoute
import com.app.ecitizen.model.ScreenEvent
import com.app.ecitizen.ui.theme.ECitizenTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun UpdateProfileScreenRoute(
    onBackClick: () -> Unit,
    navigateToHome: () -> Unit,
    profileScreenViewModel: UpdateProfileViewModel = hiltViewModel(),
) {
    val user by profileScreenViewModel.user.collectAsStateWithLifecycle(initialValue = null)

    BackHandler(user == null) {

    }

    val scaffoldState = rememberScaffoldState()

    val context = LocalContext.current

    LaunchedEffect(true) {
        profileScreenViewModel
            .screenEvent
            .collectLatest { event ->
                when (event) {
                    is ScreenEvent.Navigate -> {
                        if (event.route == homeNavigationRoute) {
                            navigateToHome()
                        } else {
                            onBackClick()
                        }
                    }

                    is ScreenEvent.ShowSnackbar.MessageResId -> {
                        scaffoldState.snackbarHostState.showSnackbar(context.getString(event.resId))
                    }

                    is ScreenEvent.ShowSnackbar.MessageString -> {
                        scaffoldState.snackbarHostState.showSnackbar(event.value)
                    }
                }
            }
    }

    UpdateProfileScreen(
        user = user,
        name = { profileScreenViewModel.name },
        ward = { profileScreenViewModel.ward },
        colony = { profileScreenViewModel.colony },
        updateName = profileScreenViewModel::updateName,
        updateWard = profileScreenViewModel::updateWard,
        updateColony = profileScreenViewModel::updateColony,
        onBackClick = onBackClick,
        scaffoldState = scaffoldState,
        updateProfile = profileScreenViewModel::updateProfile,
        isLoading = profileScreenViewModel.isLoading
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProfileScreen(
    user: UserDto?,
    name: () -> String,
    ward: () -> String,
    colony: () -> String,
    updateName: (String) -> Unit,
    updateWard: (String) -> Unit,
    updateColony: (String) -> Unit,
    onBackClick: () -> Unit,
    updateProfile: () -> Unit,
    scaffoldState: ScaffoldState,
    isLoading: Boolean,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.update_profile),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    if (user != null)
                        IconButton(onClick = onBackClick) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_back),
                                contentDescription = null
                            )
                        }
                },
            )
        },
        scaffoldState = scaffoldState
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(20.dp),

            horizontalAlignment = Alignment.CenterHorizontally

        ) {


            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(R.string.full_name),
                style = MaterialTheme.typography.labelLarge
            )

            NameTextField(name, updateName)

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                text = stringResource(R.string.ward_number),
                style = MaterialTheme.typography.labelLarge
            )

            WardTextField(ward, updateWard)


            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                text = stringResource(R.string.colony_name),
                style = MaterialTheme.typography.labelLarge
            )

            ColonyTextField(colony, updateColony)

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .height(50.dp),

                    )

            } else {
                Button(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .height(50.dp)
                        .fillMaxWidth(),
                    onClick = updateProfile,
                    shape = MaterialTheme.shapes.extraSmall
                ) {
                    Text(text = stringResource(R.string.submit))
                }
            }

        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ColonyTextField(colony: () -> String, updateColony: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 6.dp),
        placeholder = { Text(text = stringResource(R.string.colony_name)) },
        value = colony(),
        onValueChange = updateColony,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        singleLine = true,
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun WardTextField(ward: () -> String, updateWard: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 6.dp),
        placeholder = { Text(text = stringResource(R.string.ward_number)) },
        value = ward(),
        onValueChange = updateWard,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun NameTextField(name: () -> String, updateName: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 6.dp),
        placeholder = { Text(text = stringResource(R.string.full_name)) },
        value = name(),
        onValueChange = updateName,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        singleLine = true,
    )
}

@Preview(showBackground = true)
@Composable
fun UpdateProfileScreenPreview() {
    ECitizenTheme {
        UpdateProfileScreen(
            user = null,
            name = { "" },
            ward = { "" },
            colony = { "" },
            updateName = {},
            updateWard = {},
            updateColony = {},
            onBackClick = {},
            updateProfile = {},
            scaffoldState = rememberScaffoldState(),
            isLoading = false
        )
    }
}
