package com.app.ecitizen.features.contact

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.ecitizen.features.auth.mobileNumberArg
import com.app.ecitizen.features.auth.otpVerificationNavigationRoute
import com.app.ecitizen.features.contact.ui.ContactScreenRoute
import com.app.ecitizen.features.telephone.phoneBook.PhoneBookScreenRoute


const val contactUsScreenNavigationRoute = "contact_us_board_route"
const val contactNavigationRoute = "contact_route"

internal const val contactUsTypeArg = "contactUs"

internal class ContactUsArgs(val type: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(Uri.decode(checkNotNull(savedStateHandle[contactUsTypeArg])))
}

fun NavController.navigateToContactUs() {
    this.navigate(contactUsScreenNavigationRoute)
}

fun NavController.navigateToContact(type:String) {
    val encodedMobileNumber = Uri.encode(type)
    this.navigate("$contactNavigationRoute/$encodedMobileNumber")
}

fun NavGraphBuilder.contactUsScreen(
    onBackClick: () -> Unit,
    navigateToPhoneBook: (String) -> Unit,
) {
    composable(route = contactUsScreenNavigationRoute) {
        ContactUsScreenRoute(
            onBackClick = onBackClick,
            navigateToPhoneBook = navigateToPhoneBook
        )
    }

    composable(
        route = "$contactNavigationRoute/{$contactUsTypeArg}"
    ) {
        ContactScreenRoute(onBackClick = onBackClick)
    }

}

