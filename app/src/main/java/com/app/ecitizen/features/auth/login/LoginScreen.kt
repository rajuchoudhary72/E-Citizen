package com.app.ecitizen.features.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.ecitizen.R
import com.app.ecitizen.ui.theme.ECitizenTheme


@Composable
fun LoginRoute(
    modifier: Modifier = Modifier,
    navigateToVerifyOtp: (String) -> Unit,
    openAppLocaleSettings: () -> Unit,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val mobileNumber by loginViewModel.mobileNumber.collectAsStateWithLifecycle()
    val isMobileValid by loginViewModel.isMobileValid.collectAsStateWithLifecycle()


    LoginScreen(
        modifier = modifier,
        mobileNumber = mobileNumber,
        updateMobileNumber = loginViewModel::updateMobileNumber,
        isMobileValid = isMobileValid,
        verifyOtp = { navigateToVerifyOtp.invoke(mobileNumber) },
        openAppLocaleSettings = openAppLocaleSettings
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier,
    mobileNumber: String,
    updateMobileNumber: (String) -> Unit,
    isMobileValid: Boolean,
    verifyOtp: () -> Unit,
    openAppLocaleSettings: () -> Unit,

    ) {
    Column(
        modifier = modifier
            .padding(20.dp)
            .fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 40.dp)
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Let's Sign You In",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Welcome back, you've been missed!",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

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



        Column(
            modifier = Modifier.fillMaxSize(),
        ) {

            Image(
                modifier = Modifier.padding(50.dp),
                painter = painterResource(id = R.drawable.welcome),
                contentDescription = null
            )
            Text(
                text = stringResource(R.string.mobile_number),
                style = MaterialTheme.typography.labelLarge
            )

            Spacer(modifier = Modifier.size(10.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = stringResource(R.string.mobile_number)) },
                value = mobileNumber,
                onValueChange = updateMobileNumber,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
            )


            Spacer(modifier = Modifier.size(20.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = verifyOtp,
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
                openAppLocaleSettings={},
                verifyOtp={},
            )
        }
    }
}





