package com.app.ecitizen.features.imagePreview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import coil.load
import com.app.ecitizen.ui.theme.ECitizenTheme
import com.jsibbold.zoomage.ZoomageView


@Composable
fun ImagePreviewRoute(
    onBackClick: () -> Unit,
    viewModel: ImagePreviewViewModel = hiltViewModel(),
) {

    ImagePreviewScreen(
        onBackClick = onBackClick,
        image = viewModel.imagePath
    )

}

@Composable
fun ImagePreviewScreen(
    onBackClick: () -> Unit,
    image: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .statusBarsPadding()
    ) {

        ZoomableImage(image = image)

        IconButton(
            modifier = Modifier.padding(16.dp),
            onClick = onBackClick
        ) {
            Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = Color.White)
        }
    }

}

@Composable
fun ZoomableImage(image: String) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            ZoomageView(context).apply {
                load(image) {
                    crossfade(true)
                }
            }
        })
}


@Preview
@Composable
fun ImagePreviewScreenPreview() {
    ECitizenTheme() {
        Surface() {
            ImagePreviewScreen(
                onBackClick = {},
                image = "https://coil-kt.github.io/coil/images/coil_logo_black.svg"
            )
        }
    }
}



