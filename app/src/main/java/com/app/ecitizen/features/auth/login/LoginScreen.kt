package com.app.ecitizen.features.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.ecitizen.R
import com.app.ecitizen.model.ScreenEvent
import com.app.ecitizen.ui.theme.ECitizenTheme
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginRoute(
    modifier: Modifier = Modifier,
    navigateToVerifyOtp: (String) -> Unit,
    openAppLocaleSettings: () -> Unit,
    loginViewModel: LoginViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState
) {

    var isMobileNumberValid by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    LaunchedEffect(true) {
        loginViewModel.screenEvent.collectLatest { event ->
            when(event){
                is ScreenEvent.Navigate -> {
                    navigateToVerifyOtp(loginViewModel.mobileNumber)
                }
                is ScreenEvent.ShowSnackbar.MessageResId -> {
                    snackbarHostState.showSnackbar(context.getString(event.resId))
                }
                is ScreenEvent.ShowSnackbar.MessageString -> {
                    snackbarHostState.showSnackbar(message = event.value)
                }
            }
        }
    }

    LaunchedEffect(loginViewModel.mobileNumber){
        isMobileNumberValid = loginViewModel.mobileNumber.length == 10
    }

    LoginScreen(
        modifier = modifier,
        mobileNumber = loginViewModel.mobileNumber,
        updateMobileNumber = loginViewModel::updateMobileNumber,
        isMobileValid = isMobileNumberValid,
        sendOtp = loginViewModel::sendOtp,
        openAppLocaleSettings = openAppLocaleSettings,
        isOtpSendLoading = loginViewModel.isLoading
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier,
    mobileNumber: String,
    updateMobileNumber: (String) -> Unit,
    isMobileValid: Boolean,
    sendOtp: () -> Unit,
    openAppLocaleSettings: () -> Unit,
    isOtpSendLoading: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.app_background),
                contentScale = ContentScale.Crop
            )
            .systemBarsPadding()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            IconButton(
                onClick = openAppLocaleSettings,
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.indian_flag),
                        contentDescription = null
                    )
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24),
                        contentDescription = null
                    )

                }
            }
        }

        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(id = R.drawable.ic_logo_big),
            contentDescription = null
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.enter_your_mobile_number),
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            text = stringResource(R.string.we_will_send_you_a_conformation_code),
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(30.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = stringResource(R.string.mobile_number)) },
            value = mobileNumber,
            onValueChange = updateMobileNumber,
            textStyle = MaterialTheme.typography.titleMedium.copy(
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Medium
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            singleLine = true,
            leadingIcon = {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.indian_flag),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "+91",
                        style = MaterialTheme.typography.titleMedium.copy(fontFamily = FontFamily.Serif)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Divider(
                        modifier = Modifier
                            .height(54.dp)
                            .width(1.dp),
                        color = MaterialTheme.colorScheme.outline
                    )
                }

            }
        )


        Spacer(modifier = Modifier.height(10.dp))

        if (isOtpSendLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .then(Modifier.size(48.dp))
            )
        } else {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = sendOtp,
                shape = MaterialTheme.shapes.extraSmall,
                enabled = isMobileValid
            ) {
                Text(text = stringResource(R.string.continue_))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    ECitizenTheme {
        ECitizenTheme {
            LoginScreen(
                modifier = Modifier,
                mobileNumber = "9876543210",
                updateMobileNumber = {},
                isMobileValid = true,
                openAppLocaleSettings = {},
                sendOtp = {},
                isOtpSendLoading = false
            )
        }
    }
}





