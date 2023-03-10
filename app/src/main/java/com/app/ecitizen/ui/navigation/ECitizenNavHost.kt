package com.app.ecitizen.ui.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.app.ecitizen.R
import com.app.ecitizen.features.about.aboutUs
import com.app.ecitizen.features.advertisement.advertisementScreen
import com.app.ecitizen.features.advertisement.navigateToAdvertisement
import com.app.ecitizen.features.auth.loginNavigationRoute
import com.app.ecitizen.features.auth.loginScreen
import com.app.ecitizen.features.auth.navigateToLoginScreen
import com.app.ecitizen.features.auth.navigateToOtpVerification
import com.app.ecitizen.features.auth.otpVerificationScreen
import com.app.ecitizen.features.commOffice.commOffice
import com.app.ecitizen.features.complaint.navigateToRegisterComplaint
import com.app.ecitizen.features.complaint.navigateToViewComplaint
import com.app.ecitizen.features.complaint.registerComplaintScreen
import com.app.ecitizen.features.contact.contactUsScreen
import com.app.ecitizen.features.contact.navigateToContact
import com.app.ecitizen.features.contact.navigateToContactUs
import com.app.ecitizen.features.download.downloadScreen
import com.app.ecitizen.features.download.navigateToDownload
import com.app.ecitizen.features.home.homeScreen
import com.app.ecitizen.features.home.navigateToHome
import com.app.ecitizen.features.imagePreview.imagePreviewScreen
import com.app.ecitizen.features.imagePreview.navigateToImagePreview
import com.app.ecitizen.features.noticeBoard.navigateToNotice
import com.app.ecitizen.features.noticeBoard.navigateToNoticeBoard
import com.app.ecitizen.features.noticeBoard.noticeBoardScreen
import com.app.ecitizen.features.notification.notificationScreen
import com.app.ecitizen.features.place.navigateToPlaces
import com.app.ecitizen.features.place.placesScreen
import com.app.ecitizen.features.profile.navigateToUpdateProfile
import com.app.ecitizen.features.profile.profileScreen
import com.app.ecitizen.features.profile.updateProfileScreenNavigationRoute
import com.app.ecitizen.features.service.navigateToService
import com.app.ecitizen.features.service.serviceScreen
import com.app.ecitizen.features.splash.splashScreen
import com.app.ecitizen.features.splash.splashScreenNavigationRoute
import com.app.ecitizen.features.telephone.navigateToPhoneBook
import com.app.ecitizen.features.telephone.navigateToTelephoneDirectory
import com.app.ecitizen.features.telephone.telephoneDirectoryScreen
import com.app.ecitizen.serviceProviderFeature.navigateToServiceProviderComplaintScreen
import com.app.ecitizen.serviceProviderFeature.serviceProviderComplaintScreen

/**
 * Top-level navigation graph. Navigation is organized as explained at
 * https://d.android.com/jetpack/compose/nav-adaptive
 *
 * The navigation graph defined in this file defines the different top level routes. Navigation
 * within each route is handled using state and Back Handlers.
 */
@Composable
fun ECitizenNavHost(
    navController: () -> NavHostController,
    onBackClick: () -> Unit,
    openAppLocaleSettings: () -> Unit,
    closeSplashScreen: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = splashScreenNavigationRoute,
    snackbarHostState: () -> SnackbarHostState,
) {
    NavHost(
        navController = navController(),
        startDestination = startDestination,
        modifier = modifier,
    ) {

        splashScreen(
            navigateToHome = {
                val navOptions = navOptions {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(splashScreenNavigationRoute) {
                        saveState = true
                        inclusive = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
                navController().navigateToHome(navOptions)
            },
            navigateToLogin = {
                val navOptions = navOptions {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(splashScreenNavigationRoute) {
                        saveState = true
                        inclusive = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
                navController().navigateToLoginScreen(navOptions)
            },
            navigateToServiceProviderComplaint = {
                val navOptions = navOptions {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(splashScreenNavigationRoute) {
                        saveState = true
                        inclusive = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
                navController().navigateToServiceProviderComplaintScreen(navOptions)
            }
        )

        loginScreen(
            snackbarHostState = snackbarHostState,
            navigateToVerifyOtp = navController()::navigateToOtpVerification,
            openAppLocaleSettings = openAppLocaleSettings,
            navigateToServiceProviderHome = {
                val navOptions = navOptions {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(loginNavigationRoute) {
                        saveState = true
                        inclusive = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
                navController().navigateToServiceProviderComplaintScreen(navOptions)
            }
        )

        otpVerificationScreen(
            snackbarHostState(),
            navigateToHome = {
                navController().navigateToHome()
            },
            onBackClick = onBackClick,
            navigateToUpdateProfile = {
                navController().navigateToUpdateProfile(it)
            }
        )

        homeScreen(
            navigateToRegisterComplaint = {
                navController().navigateToRegisterComplaint()
            },
            navigateToViewComplaints = {
                navController().navigateToViewComplaint()
            },
            navigateToService = { service ->
                when (service.name) {
                    R.string.notice_board -> {
                        navController().navigateToNoticeBoard()
                    }

                    R.string.services -> {
                        navController().navigateToService()
                    }

                    R.string.downloads -> {
                        navController().navigateToDownload()
                    }

                    R.string.important_places -> {
                        navController().navigateToPlaces()
                    }

                    R.string.advertisements -> {
                        navController().navigateToAdvertisement()
                    }

                    R.string.telephone_directory -> {
                        navController().navigateToTelephoneDirectory()
                    }

                    R.string.about_us -> {
                        navController().navigateToContactUs()
                    }
                }

            })

        aboutUs(onBackClick)

        imagePreviewScreen(onBackClick)

        commOffice(onBackClick)

        profileScreen(
            onBackClick = onBackClick,
            navigateToUpdateProfile = { navController().navigateToUpdateProfile(it) },
            navigateToHome = {
                val navOptions = navOptions {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(updateProfileScreenNavigationRoute) {
                        saveState = true
                        inclusive = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
                navController().navigateToHome(navOptions)
            },
            showAppLocaleDialog = openAppLocaleSettings
        )

        noticeBoardScreen(
            onBackClick = onBackClick,
            navigateToNotice = {
                navController().navigateToNotice(it)
            },
            navigateToImagePreview = {
                navController().navigateToImagePreview(it)
            }
        )

        serviceScreen(
            onBackClick = onBackClick,
        )

        downloadScreen(
            onBackClick = onBackClick,
        )

        placesScreen(
            onBackClick = onBackClick,
        )

        registerComplaintScreen(
            onBackClick = onBackClick,
            navigateToImagePreview = {
                navController().navigateToImagePreview(it)
            }
        )

        advertisementScreen(
            onBackClick = onBackClick,
            navigateToImagePreview = {
                navController().navigateToImagePreview(it)
            }
        )
        notificationScreen(
            onBackClick = onBackClick
        )

        telephoneDirectoryScreen(
            onBackClick = onBackClick,
            navigateToPhoneBook = { navController().navigateToPhoneBook(it) }
        )

        contactUsScreen(
            onBackClick = onBackClick,
            navigateToPhoneBook = { navController().navigateToContact(it) }
        )

        serviceProviderComplaintScreen()
    }
}
