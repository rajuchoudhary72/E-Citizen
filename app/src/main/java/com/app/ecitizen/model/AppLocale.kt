package com.app.ecitizen.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.app.ecitizen.R

enum class AppLocale(@StringRes val nameRes: Int, @DrawableRes val flag: Int, val code:String) {
    HINDI(R.string.hindi, R.drawable.indian_flag, "hi"),
    ENGLISH(R.string.english, R.drawable.usa_flag,"en");

    companion object {
        fun getAppLocale(code:String?) = values().firstOrNull { it.code == code }?:HINDI
    }
}