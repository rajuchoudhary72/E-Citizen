package com.app.ecitizen.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.ecitizen.R
import com.app.ecitizen.model.AppError
import com.app.ecitizen.model.TextValue
import com.app.ecitizen.utils.toTextValue

@Composable
fun ErrorAndLoadingScreen(
    isLoading: Boolean = false,
    error: AppError? = null
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            LoadingWheel(
                contentDesc = stringResource(id = R.string.loading)
            )
        } else {
           error?:return@Box

            val errorMessage = when (val text = error.toTextValue()) {
                is TextValue.Text -> {
                    text.value
                }

                is TextValue.TextResId -> {
                    context.getString(text.resId)
                }
            }
            Text(
                text = errorMessage,
                textAlign = TextAlign.Center
            )
        }

    }
}