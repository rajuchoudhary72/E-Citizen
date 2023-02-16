package com.app.ecitizen.features.profile

import android.content.Intent
import androidx.activity.ComponentActivity
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.GTranslate
import androidx.compose.material3.Button
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.ecitizen.BuildConfig
import com.app.ecitizen.R
import com.app.ecitizen.data.network.dto.UserDto
import com.app.ecitizen.model.ScreenEvent
import com.app.ecitizen.ui.MainActivity
import com.app.ecitizen.ui.theme.ECitizenTheme
import com.app.ecitizen.ui.theme.Purple95
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProfileScreenRoute(
    onBackClick: () -> Unit,
    showAppLocaleDialog: () -> Unit,
    profileScreenViewModel: ProfileScreenViewModel = hiltViewModel(),
    navigateToUpdateProfile: (String) -> Unit,
) {
    val user by profileScreenViewModel.user.collectAsStateWithLifecycle(initialValue = null)

    val context = LocalContext.current as ComponentActivity

    LaunchedEffect(true) {
        profileScreenViewModel
            .screenEvent
            .collectLatest { event ->
                when (event) {
                    is ScreenEvent.Navigate -> {
                        context.finishAffinity()
                        context.startActivity(Intent(context, MainActivity::class.java))
                    }

                    is ScreenEvent.ShowSnackbar.MessageResId -> {

                    }

                    is ScreenEvent.ShowSnackbar.MessageString -> {

                    }
                }
            }
    }

    ProfileScreen(
        onBackClick,
        user,
        navigateToUpdateProfile = { navigateToUpdateProfile(user!!.mobile) },
        logout = profileScreenViewModel::logout,
        showAppLocaleDialog = showAppLocaleDialog
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onBackClick: () -> Unit,
    user: UserDto?,
    navigateToUpdateProfile: () -> Unit,
    logout: () -> Unit,
    showAppLocaleDialog: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.profile),
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
                },
                actions = {

                    IconButton(onClick = showAppLocaleDialog) {
                        Icon(imageVector = Icons.Outlined.GTranslate, contentDescription = null)
                    }

                    IconButton(onClick = navigateToUpdateProfile) {
                        Icon(imageVector = Icons.Outlined.Edit, contentDescription = null)
                    }
                }
            )
        },
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
                    .padding(top = 20.dp)
                    .drawBehind {
                        drawCircle(
                            color = Purple95,
                            radius = 140f
                        )
                    },
                text = user?.name?.toCharArray()?.first()?.toUpperCase().toString(),
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center,

                )

            Text(
                modifier = Modifier.padding(top = 30.dp),
                text = user?.name ?: "",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                modifier = Modifier
                    .padding(top = 40.dp)
                    .fillMaxWidth(),
                text = stringResource(R.string.full_name),
                style = MaterialTheme.typography.labelLarge
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                placeholder = { Text(text = stringResource(R.string.mobile_number)) },
                value = user?.name ?: "",
                onValueChange = { },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true,
                readOnly = true
            )


            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                text = stringResource(R.string.mobile_number),
                style = MaterialTheme.typography.labelLarge
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                placeholder = { Text(text = stringResource(R.string.mobile_number)) },
                value = user?.mobile ?: "",
                onValueChange = { },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                readOnly = true
            )


            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                text = stringResource(R.string.ward_number),
                style = MaterialTheme.typography.labelLarge
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                placeholder = { Text(text = stringResource(R.string.ward_number)) },
                value = user?.ward ?: "",
                onValueChange = { },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                readOnly = true
            )


            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                text = stringResource(R.string.colony_name),
                style = MaterialTheme.typography.labelLarge
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                placeholder = { Text(text = stringResource(R.string.colony_name)) },
                value = user?.colony ?: "",
                onValueChange = { },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true,
                readOnly = true
            )


            Button(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .height(50.dp)
                    .fillMaxWidth(),
                onClick = logout,
                shape = MaterialTheme.shapes.extraSmall
            ) {
                Text(text = stringResource(R.string.logout))
            }

            Text(
                modifier = Modifier.padding(top = 30.dp),
                text = "Version - ${BuildConfig.VERSION_NAME}",
                style = MaterialTheme.typography.labelSmall
            )


        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    ECitizenTheme {
        ProfileScreen({}, null, { }, { }, { })
    }
}
