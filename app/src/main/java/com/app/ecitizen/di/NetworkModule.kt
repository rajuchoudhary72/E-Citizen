package com.app.ecitizen.di

import com.app.ecitizen.BuildConfig
import com.app.ecitizen.data.network.AuthTokenInterceptor
import com.app.ecitizen.data.network.NetworkResponseInterceptor
import com.app.ecitizen.data.network.RetrofitService
import com.app.ecitizen.utils.FlowCallAdapterFactory
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideGson():Gson{
        return Gson().apply {

        }
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }


    @Singleton
    @Provides
    fun provideHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        tokenAuthenticator: AuthTokenInterceptor,
        networkConnectionInterceptor: NetworkResponseInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(networkConnectionInterceptor)
            .addInterceptor(tokenAuthenticator)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient,

        ): RetrofitService {

        return Retrofit
            .Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(FlowCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitService::class.java)
    }

}