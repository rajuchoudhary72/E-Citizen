package com.app.ecitizen.features.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.app.ecitizen.BuildConfig
import com.app.ecitizen.R
import com.app.ecitizen.ui.components.LoadingWheel
import com.app.ecitizen.ui.theme.ECitizenTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce

@Composable
fun SplashScreenRoute(
    navigateToLogin: () -> Unit,
    navigateToHome: () -> Unit,
    splashViewModel: SplashViewModel = hiltViewModel(),
) {

    val uiState: SplashScreenUiState by splashViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        splashViewModel
            .userDto
            .debounce(3000)
            .collectLatest { userDto ->
                if (userDto != null) {
                    navigateToHome()
                } else {
                    navigateToLogin()
                }
            }
    }

    SplashScreen(
        uiState = uiState
    )

}

@Composable
fun SplashScreen(
    uiState: SplashScreenUiState
) {

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        when (uiState) {
            SplashScreenUiState.Loading -> {
                LoadingWheel(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .padding(20.dp),
                    contentDesc = stringResource(R.string.loading)
                )
            }

            is SplashScreenUiState.Success -> {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    AsyncImage(
                        modifier = Modifier.size(150.dp).background(color = Color.White, shape = RoundedCornerShape(50)).padding(20.dp),
                        model = "${BuildConfig.SERVER_URL}${uiState.appFront?.basePath}${uiState.appFront?.logo}",
                        placeholder = painterResource(id = R.drawable.ic_splash_logo),
                        error = painterResource(id = R.drawable.ic_splash_logo),
                        contentDescription = null,
                        contentScale = ContentScale.Inside
                    )

                    Text(
                        modifier = Modifier.padding(top = 20.dp),
                        text = uiState.appFront?.buttion1Name
                            ?: context.getString(R.string.app_name),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    if (uiState.appFront?.buttion2Name.isNullOrEmpty().not())
                        Text(
                            text = uiState.appFront?.buttion2Name ?: "",
                            style = MaterialTheme.typography.bodyMedium
                        )
                }
                if (uiState.appFront?.buttion3Name.isNullOrEmpty().not())
                    Text(
                        modifier = Modifier.padding(bottom = 20.dp),
                        text = ("Powered By: " + uiState.appFront?.buttion3Name),
                        style = MaterialTheme.typography.bodyMedium
                    )

            }
        }

    }


}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    ECitizenTheme {
        SplashScreen(SplashScreenUiState.Success(null))
    }
}
