package com.app.ecitizen.features.auth.otp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.ecitizen.R
import com.app.ecitizen.features.profile.updateProfileScreenNavigationRoute
import com.app.ecitizen.model.ScreenEvent
import com.app.ecitizen.ui.components.PinView
import com.app.ecitizen.ui.theme.ECitizenTheme
import kotlinx.coroutines.flow.collectLatest


@Composable
fun OtpVerificationRoute(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit,
    onBackClick: () -> Unit,
    otpViewModel: OtpVerificationViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    navigateToUpdateProfile: (String) -> Unit,
) {
    val context = LocalContext.current

    var isOtpFilled by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(otpViewModel.otp) {
        isOtpFilled = otpViewModel.otp.length == 4
    }

    LaunchedEffect(true){
        otpViewModel
            .screenEvent
            .collectLatest { event ->
                when(event) {
                    is ScreenEvent.Navigate -> {
                        if (event.route== updateProfileScreenNavigationRoute) {
                            navigateToUpdateProfile(otpViewModel.mobileNumber)
                        }else{
                            navigateToHome()
                        }
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


    OtpVerificationScreen(
        modifier = modifier,
        mobileNumber = otpViewModel.mobileNumber,
        otp = otpViewModel.otp,
        updateOtp = otpViewModel::updateOtp,
        isOtpFilled = isOtpFilled,
        onBackClick = onBackClick,
        verifyOtp = otpViewModel::verifyOtp,
        resendOtp = otpViewModel::resendOtp,
        navigateToHome = navigateToHome,
        otpVerificationLoading = otpViewModel.otpVerificationLoading,
        otpResendLoading = otpViewModel.otpResendLoading
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpVerificationScreen(
    modifier: Modifier,
    mobileNumber: String,
    otp: String,
    updateOtp: (String) -> Unit,
    isOtpFilled: Boolean,
    onBackClick: () -> Unit,
    verifyOtp: () -> Unit,
    resendOtp: () -> Unit,
    navigateToHome: () -> Unit,
    otpVerificationLoading: Boolean,
    otpResendLoading: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            onBackClick
        )

        Image(
            modifier = modifier.padding(40.dp),
            painter = painterResource(id = R.drawable.img_otp),
            contentDescription = null
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.verification_code),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.we_have_sent_the_code_verification_to_your_mobile_number),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "(+91) $mobileNumber",
                style = MaterialTheme.typography.titleMedium,
            )

            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_edit_24),
                    contentDescription = null
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            PinView(
                pinText = otp,
                onPinTextChange = updateOtp,
                digitSize = 30.sp
            )
        }

        if (otpVerificationLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(top = 40.dp)
                    .height(50.dp),

            )
        } else {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
                    .height(50.dp),
                onClick = verifyOtp,
                shape = MaterialTheme.shapes.extraSmall,
                enabled = isOtpFilled
            ) {
                Text(text = stringResource(R.string.submit))
            }
        }


        if(otpResendLoading){
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(top = 40.dp)
                    .height(50.dp),

                )
        }else{
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.haven_t_you_received_the_sms)
                )
                TextButton(
                    onClick = resendOtp,
                    shape = MaterialTheme.shapes.extraSmall,
                ) {
                    Text(text = stringResource(R.string.resend_code))
                }
            }
        }




    }
}

@Composable
private fun TopAppBar(onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        contentAlignment = Alignment.CenterStart
    ) {

        IconButton(onClick = onBackClick) {
            Icon(painter = painterResource(id = R.drawable.ic_back), contentDescription = null)
        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.otp_verification),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )

    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    ECitizenTheme {
        OtpVerificationScreen(
            modifier = Modifier,
            mobileNumber = "987654321",
            otp = "9876",
            updateOtp = {},
            isOtpFilled = true,
            onBackClick = {},
            verifyOtp = {},
            resendOtp = {},
            navigateToHome = {},
            otpVerificationLoading = false,
            otpResendLoading = false,
        )
    }

}





