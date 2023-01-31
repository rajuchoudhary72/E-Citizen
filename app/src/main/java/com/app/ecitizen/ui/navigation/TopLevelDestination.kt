package com.app.ecitizen.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.app.ecitizen.R

enum class TopLevelDestination(
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
    @StringRes val iconTextId: Int,
    @StringRes val titleTextId: Int,
) {
    HOME(
        selectedIcon = R.drawable.baseline_home_24,
        unselectedIcon = R.drawable.outline_home_24,
        iconTextId = R.string.home,
        titleTextId = R.string.home,
    ),
    COMMISSIONER_OFFICE(
        selectedIcon = R.drawable.outline_cabin_24,
        unselectedIcon = R.drawable.outline_cabin_24,
        iconTextId = R.string.commissioner_office,
        titleTextId = R.string.commissioner_office,
    ),
    CONTACT_US(
        selectedIcon = R.drawable.baseline_contact_support_24,
        unselectedIcon = R.drawable.outline_contact_support_24,
        iconTextId = R.string.contact_us,
        titleTextId = R.string.contact_us,
    ),
    PROFILE(
        selectedIcon = R.drawable.baseline_person_24,
        unselectedIcon = R.drawable.outline_person_24,
        iconTextId = R.string.profile,
        titleTextId = R.string.profile,
    )
}
