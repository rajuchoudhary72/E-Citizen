package com.app.ecitizen.utils

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.annotation.StringRes
import com.app.ecitizen.R
import com.app.ecitizen.data.network.dto.ApiError
import com.app.ecitizen.data.network.exception.BadRequestException
import com.app.ecitizen.data.network.exception.NetworkNotConnectedException


import retrofit2.HttpException

import com.app.ecitizen.model.AppError
import com.app.ecitizen.model.ErrorType
import com.app.ecitizen.model.ScreenEvent
import com.app.ecitizen.model.TextValue
import com.google.gson.Gson
import org.w3c.dom.Text

/**
 * Convert Throwable to AppError
 */
fun Throwable?.toAppError(): AppError? {
    this?.printStackTrace()
    return when (this) {
        null -> null
        is AppError -> this
        is NetworkNotConnectedException -> AppError.ApiException.NetworkNotConnectedException(this)
        is HttpException -> this.httpExceptionToAppError()
        is BadRequestException -> AppError.ApiException.BadRequestException(this)
        else -> AppError.UnknownException(this)
    }
}

private fun HttpException.httpExceptionToAppError(): AppError {
    val errorResponse = response()
    return if (errorResponse != null) {
        val error = errorResponse.errorBody()?.string()
        if (error != null && error.isEmpty().not()) {
            try {
                AppError.ApiException.BadRequestException(
                    BadRequestException(
                        Gson().fromJson(error, ApiError::class.java)
                    )
                )
            } catch (e: Exception) {
                when (ErrorType.getErrorType(response()!!.code())) {
                    ErrorType.UNAUTHORIZED ->
                        AppError.ApiException.SessionNotFoundException(this)

                    ErrorType.SYSTEM_ERROR ->
                        AppError.ApiException.ServerException(this)

                    else ->
                        AppError.ApiException.NetworkException(this)
                }
            }
        } else {
            when (ErrorType.getErrorType(response()!!.code())) {
                ErrorType.UNAUTHORIZED ->
                    AppError.ApiException.SessionNotFoundException(this)

                ErrorType.SYSTEM_ERROR ->
                    AppError.ApiException.ServerException(this)

                else ->
                    AppError.ApiException.NetworkException(this)
            }
        }
    } else {
        AppError.ApiException.UnknownException(this)
    }
}


/**
 * Convert AppError to String Resources
 */
@StringRes
fun AppError.stringRes() = when (this) {
    is AppError.ApiException.NetworkException -> R.string.error_network
    is AppError.ApiException.NetworkNotConnectedException -> R.string.error_no_internet_connection
    is AppError.ApiException.ServerException -> R.string.error_server
    is AppError.ApiException.SessionNotFoundException -> R.string.error_unknown
    is AppError.ApiException.UnknownException -> R.string.error_unknown
    is AppError.UnknownException -> R.string.error_unknown
    else -> R.string.error_unknown
}



fun AppError.toScreenEvent(): ScreenEvent {
  return  if (this is AppError.ApiException.BadRequestException) {
        ScreenEvent.ShowSnackbar.MessageString((this.cause as BadRequestException).apiError.message?:"Unknown Error")
    } else {
        ScreenEvent.ShowSnackbar.MessageResId(this.stringRes())
    }
}
fun AppError.toTextValue(): TextValue {
  return  if (this is AppError.ApiException.BadRequestException) {
      TextValue.Text((this.cause as BadRequestException).apiError.message?:"Unknown Error")
    } else {
      TextValue.TextResId(this.stringRes())
    }
}

fun Activity.getRootView(): View {
    return findViewById<View>(android.R.id.content)
}

fun Context.convertDpToPx(dp: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        this.resources.displayMetrics
    )
}

fun Activity.isKeyboardOpen(): Boolean {
    val visibleBounds = Rect()
    this.getRootView().getWindowVisibleDisplayFrame(visibleBounds)
    val heightDiff = getRootView().height - visibleBounds.height()
    val marginOfError = Math.round(this.convertDpToPx(50F))
    return heightDiff > marginOfError
}

fun Activity.isKeyboardClosed(): Boolean {
    return !this.isKeyboardOpen()
}