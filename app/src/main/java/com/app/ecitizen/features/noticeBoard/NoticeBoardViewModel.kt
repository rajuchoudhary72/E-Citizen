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
    val icon: Int,
    val endPoint:String
) {

    companion object {
        val NOTICES = mutableListOf(
            Notice(
                name = R.string.general_notice,
                icon = R.drawable.general_notice,
                endPoint = "General notice"
            ),
            Notice(
                name = R.string.suggestion,
                icon = R.drawable.notice_suggestion,
                endPoint = "Suggession"
            ),
            Notice(
                name = R.string.scheme,
                icon = R.drawable.notice_scheme,
                endPoint = "Scheme"
            ),
            Notice(
                name = R.string.news,
                icon = R.drawable.news,
                endPoint = "News"
            ),
        )
    }
}





