package com.app.ecitizen.model

sealed interface ScreenEvent {
    sealed interface ShowSnackbar : ScreenEvent{
        data class MessageResId(val resId: Int) : ShowSnackbar
        data class MessageString(val value: String) : ShowSnackbar
    }

    data class Navigate(val route: String) : ScreenEvent
}

