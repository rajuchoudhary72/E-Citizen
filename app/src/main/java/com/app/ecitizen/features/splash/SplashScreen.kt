package com.app.ecitizen.features.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.ecitizen.R
import com.app.ecitizen.ui.theme.ECitizenTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreenRoute(
    closeSplashScreen: () -> Unit,
    splashViewModel: SplashViewModel = hiltViewModel(),
) {

    LaunchedEffect(true){
        delay(2000)
        closeSplashScreen()
    }

    SplashScreen(

    )

}

@Composable
fun SplashScreen(
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.app_background),
                contentScale = ContentScale.Crop
            ),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.india_ashoka),
                contentDescription = null
            )

            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Welcome back, you've been missed!",
                style = MaterialTheme.typography.bodyMedium
            )
        }


    }

}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    ECitizenTheme {
        SplashScreen()
    }
}
