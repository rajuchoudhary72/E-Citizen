package com.app.ecitizen.features.noticeBoard

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.ecitizen.features.auth.mobileNumberArg
import com.app.ecitizen.features.auth.otpVerificationNavigationRoute
import com.app.ecitizen.features.noticeBoard.notice.NoticeScreenRoute


const val noticeBoardScreenNavigationRoute = "notice_board_route"
const val noticeScreenNavigationRoute = "notice_route"

internal const val noticeTypeArg = "noticeType"

internal class NoticeBoardArgs(val noticeType: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(Uri.decode(checkNotNull(savedStateHandle[noticeTypeArg])))
}

fun NavController.navigateToNoticeBoard() {
    this.navigate(noticeBoardScreenNavigationRoute)
}

fun NavController.navigateToNotice(noticeType:String) {
    val encodedMobileNumber = Uri.encode(noticeType)
    this.navigate("$noticeScreenNavigationRoute/$noticeType")
}

fun NavGraphBuilder.noticeBoardScreen(
    onBackClick: () -> Unit,
    navigateToNotice: (String) -> Unit,
) {
    composable(route = noticeBoardScreenNavigationRoute) {
        NoticeBoardScreenRoute(
            onBackClick = onBackClick,
            navigateToNotice = navigateToNotice
        )
    }

    composable( route = "$noticeScreenNavigationRoute/{$noticeTypeArg}") {
        NoticeScreenRoute(onBackClick = onBackClick)
    }

}

