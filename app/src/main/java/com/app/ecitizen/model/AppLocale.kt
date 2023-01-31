package com.app.ecitizen.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.app.ecitizen.R

enum class AppLocale(@StringRes val nameRes: Int, @DrawableRes val flag: Int) {
    HINDI(R.string.hindi, R.drawable.indian_flag),
    ENGLISH(R.string.english, R.drawable.usa_flag)
}