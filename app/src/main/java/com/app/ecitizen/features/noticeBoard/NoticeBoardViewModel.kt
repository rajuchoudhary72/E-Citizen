package com.app.ecitizen.features.noticeBoard

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material.icons.twotone.MedicalInformation
import androidx.compose.material.icons.twotone.Newspaper
import androidx.compose.material.icons.twotone.Note
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import com.app.ecitizen.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NoticeBoardViewModel @Inject constructor(
) : ViewModel() {

    val notices = MutableStateFlow(Notice.NOTICES).asStateFlow()


}


data class Notice(
    @StringRes val name: Int,
    val icon: ImageVector,
) {

    companion object {
        val NOTICES = mutableListOf(
            Notice(
                name = R.string.general_notice,
                icon = Icons.TwoTone.Note,
            ),
            Notice(
                name = R.string.suggestion,
                icon = Icons.TwoTone.Info,
            ),
            Notice(
                name = R.string.scheme,
                icon = Icons.TwoTone.MedicalInformation,
            ),
            Notice(
                name = R.string.news,
                icon = Icons.TwoTone.Newspaper,
            ),
        )
    }
}





