package com.app.ecitizen.data.network

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.app.ecitizen.data.network.dto.ApiError
import com.app.ecitizen.data.network.exception.BadRequestException
import com.app.ecitizen.data.network.exception.NetworkNotConnectedException
import com.app.ecitizen.ui.MainActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkResponseInterceptor @Inject constructor(
    @ApplicationContext val context: Context,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        if (!isConnected()) {
            throw NetworkNotConnectedException()
        }

        val builder: Request.Builder = chain.request().newBuilder()

        val response = chain.proceed(builder.build())

        if (response.isSuccessful) {

            val responseString = response.peekBody(1000000000).string()

            Log.e("Response Interceptor", responseString)

            val responseJson = JSONObject(responseString)

            when (val code = responseJson.optInt("status")) {
                3 -> {
                    val intent =  Intent(MainActivity.APP_LOCAL_BROADCAST)
                    intent.putExtra("action", MainActivity.ACTION_LOGOUT)
                    LocalBroadcastManager.getInstance(context)
                        .sendBroadcast(intent)
                    Log.e("Response Interceptor", "User Unauthorised")
                }

                200 -> {
                    Log.e("Response Interceptor", "Success Response")
                }

                else -> {
                    val message = responseJson.optString("message")
                    /*Handler(Looper.getMainLooper()).post {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }*/
                    throw BadRequestException(ApiError(code, message))
                }
            }
        }

        return response
    }


    private fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}
