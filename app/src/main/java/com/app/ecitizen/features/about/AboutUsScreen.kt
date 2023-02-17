package com.app.ecitizen.features.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.ecitizen.R
import com.app.ecitizen.ui.theme.ECitizenTheme

@Composable
fun AboutUsRoute(
    onBackClick: () -> Unit,
) {
    AboutUsScreen(onBackClick)
}

@Composable
fun AboutUsScreen(
    onBackClick: () -> Unit,
) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.contact_us)) },
                backgroundColor = MaterialTheme.colorScheme.surface,
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = null
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            InfoRow(
                title = stringResource(R.string.khandela_contact_person),
                description = stringResource(R.string.name_officer),
            )

            InfoRow(
                title = stringResource(R.string.address_palikha),
                description = stringResource(R.string.address_plakha)
            )

            InfoRow(
                title = stringResource(R.string.phone_number_palikha),
                description = "01575-260029"
            )
            InfoRow(
                title = stringResource(R.string.comiss_email),
                description = "khandela.ulb@gmail.com"
            )

        }
    }

}

@Composable
private fun InfoRow(
    title: String,
    description: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp)
    ) {

        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = ":"
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
fun SplashScreenPreview() {
    ECitizenTheme {
        AboutUsScreen({})
    }
}
