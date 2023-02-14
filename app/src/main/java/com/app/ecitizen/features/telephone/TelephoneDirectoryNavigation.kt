package com.app.ecitizen.features.telephone

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.ecitizen.features.telephone.phoneBook.PhoneBookScreenRoute


const val telephoneDirectoryScreenNavigationRoute = "telephone_directory_board_route"
const val phoneBookNavigationRoute = "phone_book_route"

fun NavController.navigateToTelephoneDirectory() {
    this.navigate(telephoneDirectoryScreenNavigationRoute)
}

fun NavController.navigateToPhoneBook() {
    this.navigate(phoneBookNavigationRoute)
}

fun NavGraphBuilder.telephoneDirectoryScreen(
    onBackClick: () -> Unit,
    navigateToPhoneBook: () -> Unit,
) {
    composable(route = telephoneDirectoryScreenNavigationRoute) {
        TelephoneDirectoryScreenRoute(
            onBackClick = onBackClick,
            navigateToPhoneBook = navigateToPhoneBook
        )
    }

    composable(route = phoneBookNavigationRoute) {
        PhoneBookScreenRoute(onBackClick = onBackClick)
    }

}

