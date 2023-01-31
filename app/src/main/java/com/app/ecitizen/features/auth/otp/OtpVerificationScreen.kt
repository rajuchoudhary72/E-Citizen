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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.ecitizen.R
import com.app.ecitizen.ui.components.PinView
import com.app.ecitizen.ui.theme.ECitizenTheme


@Composable
fun OtpVerificationRoute(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit,
    onBackClick: () -> Unit,
    otpViewModel: OtpVerificationViewModel = hiltViewModel()
) {
    val otp by otpViewModel.otp.collectAsStateWithLifecycle()
    val isOtpFilled by otpViewModel.isMobileValid.collectAsStateWithLifecycle()


    OtpVerificationScreen(
        modifier = modifier,
        mobileNumber = otpViewModel.mobileNumber,
        otp = otp,
        updateOtp = otpViewModel::updateOtp,
        isOtpFilled = isOtpFilled,
        onBackClick = onBackClick,
        verifyOtp = otpViewModel::verifyOtp,
        resendOtp = otpViewModel::resendOtp,
        navigateToHome = navigateToHome
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
    navigateToHome: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(20.dp)
            .fillMaxSize()
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


        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
                .height(50.dp),
            onClick = navigateToHome,
            shape = MaterialTheme.shapes.extraSmall,
            enabled = isOtpFilled
        ) {
            Text(text = stringResource(R.string.submit))
        }

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
            text = "OTP Verification",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )

    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    ECitizenTheme() {
        OtpVerificationScreen(
            modifier = Modifier,
            mobileNumber = "987654321",
            otp = "9876",
            updateOtp = {},
            isOtpFilled = true,
            verifyOtp = {},
            onBackClick = {},
            resendOtp = {},
            navigateToHome = {},
        )
    }

}





