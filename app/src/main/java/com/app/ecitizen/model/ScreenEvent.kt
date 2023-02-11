package com.app.ecitizen.model

import androidx.annotation.Keep

@Keep
sealed interface ScreenEvent {
    sealed interface ShowSnackbar : ScreenEvent{
        data class MessageResId(val resId: Int) : ShowSnackbar
        data class MessageString(val value: String) : ShowSnackbar
    }

    data class Navigate(val route: String) : ScreenEvent
}

@Keep
sealed interface TextValue {
    data class TextResId(val resId: Int) : TextValue
    data class Text(val value: String) : TextValue

}

