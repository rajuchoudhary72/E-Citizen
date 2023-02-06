package com.app.ecitizen.features.complaint

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.app.ecitizen.R
import com.app.ecitizen.ui.theme.ECitizenTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ComplaintsDialog(
    onDismiss: () -> Unit,
    navigateToRegisterComplaint: () -> Unit,
    navigateToViewComplaints: () -> Unit
) {
    val configuration = LocalConfiguration.current

    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.widthIn(max = configuration.screenWidthDp.dp - 80.dp),
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = stringResource(R.string.register_complaints),
                style = MaterialTheme.typography.titleLarge,
            )
        },
        text = {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                item {
                    ComplaintGridItem(
                        text = stringResource(R.string.post_a_complaint),
                        onClick = navigateToRegisterComplaint
                    )
                }

                item {
                    ComplaintGridItem(
                        text = stringResource(R.string.view_complaint),
                        onClick = navigateToViewComplaints
                    )
                }

            }

        },
        confirmButton = { }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComplaintGridItem(
    text: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
                text = text,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center
            )
        }

    }
}

@Preview
@Composable
fun AppLocaleDialogPreview() {
    ECitizenTheme {
        ComplaintsDialog(
            {}, {}, {}
        )
    }
}
