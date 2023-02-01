package com.app.ecitizen.features.noticeBoard

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.ecitizen.features.noticeBoard.notice.NoticeScreenRoute


const val noticeBoardScreenNavigationRoute = "notice_board_route"
const val noticeScreenNavigationRoute = "notice_route"

fun NavController.navigateToNoticeBoard() {
    this.navigate(noticeBoardScreenNavigationRoute)
}

fun NavController.navigateToNotice() {
    this.navigate(noticeScreenNavigationRoute)
}
fun NavGraphBuilder.noticeBoardScreen(
    onBackClick: () -> Unit,
    navigateToNotice: () -> Unit,
) {
    composable(route = noticeBoardScreenNavigationRoute) {
        NoticeBoardScreenRoute(
            onBackClick = onBackClick,
            navigateToNotice = navigateToNotice
        )
    }

    composable(route = noticeScreenNavigationRoute) {
        NoticeScreenRoute(onBackClick = onBackClick)
    }

}

