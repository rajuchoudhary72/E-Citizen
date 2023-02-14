package com.app.ecitizen.features.telephone

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.ecitizen.features.auth.mobileNumberArg
import com.app.ecitizen.features.auth.otpVerificationNavigationRoute
import com.app.ecitizen.features.telephone.phoneBook.PhoneBookScreenRoute


const val telephoneDirectoryScreenNavigationRoute = "telephone_directory_board_route"
const val phoneBookNavigationRoute = "phone_book_route"

internal const val phoneBookTypeArg = "phoneBookType"

internal class PhoneBookArgs(val phoneBookType: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(Uri.decode(checkNotNull(savedStateHandle[phoneBookTypeArg])))
}

fun NavController.navigateToTelephoneDirectory() {
    this.navigate(telephoneDirectoryScreenNavigationRoute)
}

fun NavController.navigateToPhoneBook(phoneBookType:String) {
    val encodedMobileNumber = Uri.encode(phoneBookType)
    this.navigate("$phoneBookNavigationRoute/$encodedMobileNumber")
}

fun NavGraphBuilder.telephoneDirectoryScreen(
    onBackClick: () -> Unit,
    navigateToPhoneBook: (String) -> Unit,
) {
    composable(route = telephoneDirectoryScreenNavigationRoute) {
        TelephoneDirectoryScreenRoute(
            onBackClick = onBackClick,
            navigateToPhoneBook = navigateToPhoneBook
        )
    }

    composable(
        route = "$phoneBookNavigationRoute/{$phoneBookTypeArg}"
    ) {
        PhoneBookScreenRoute(onBackClick = onBackClick)
    }

}

