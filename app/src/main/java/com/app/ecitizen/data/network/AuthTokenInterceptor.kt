package com.app.ecitizen.data.network

import android.content.Context
import com.app.ecitizen.data.datastore.ECitizenPreferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthTokenInterceptor @Inject constructor(
    @ApplicationContext val context: Context,
    private val preferencesDataStore: ECitizenPreferencesDataStore,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()


        requestBuilder.addHeader("Content-Type", "application/json")
        requestBuilder.addHeader("accept", "application/json")
        requestBuilder.addHeader("language", "hi")


        runBlocking {
            preferencesDataStore.getAuthToken()
        }?.let { authToken ->
            requestBuilder.addHeader("Authorization", authToken)

        }

        return chain.proceed(requestBuilder.build())
    }
}
