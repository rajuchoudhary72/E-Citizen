package com.app.ecitizen.features.complaint

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.ecitizen.features.complaint.register.RegisterComplaintRoute
import com.app.ecitizen.features.complaint.view.ViewModelScreenRoute


const val registerComplaintScreenNavigationRoute = "register_complaint_route"
const val viewComplaintScreenNavigationRoute = "view_complaint_route"

fun NavController.navigateToRegisterComplaint() {
    this.navigate(registerComplaintScreenNavigationRoute)
}

fun NavController.navigateToViewComplaint() {
    this.navigate(viewComplaintScreenNavigationRoute)
}

fun NavGraphBuilder.registerComplaintScreen(
    onBackClick: () -> Unit,
    navigateToImagePreview: (String) -> Unit,
) {
    composable(route = registerComplaintScreenNavigationRoute) {
        RegisterComplaintRoute(
            onBackClick = onBackClick,
        )
    }
    composable(route = viewComplaintScreenNavigationRoute) {
        ViewModelScreenRoute(
            onBackClick = onBackClick,
            navigateToImagePreview  = navigateToImagePreview
        )
    }

}

