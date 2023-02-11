package com.app.ecitizen.data.network

import com.app.ecitizen.data.network.dto.AdvertisementDto
import com.app.ecitizen.data.network.dto.AppFront
import com.app.ecitizen.data.network.dto.NetworkResponse
import com.app.ecitizen.data.network.dto.NotificationDto
import com.app.ecitizen.data.network.dto.OtpResponseDto
import com.app.ecitizen.data.network.dto.SliderImage
import com.app.ecitizen.data.network.dto.UserDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RetrofitService {
    @GET("api/app-front")
    fun getAppFronts(): Flow<NetworkResponse<List<AppFront>>>

    @POST("api/otp-send")
    @FormUrlEncoded
    fun sendOtp(
        @Field("phone_no") mobileNumber: String
    ): Flow<OtpResponseDto>

    @POST("api/otp-match")
    @FormUrlEncoded
    fun verifyOtp(
        @Field("phone_no") mobileNumber: String,
        @Field("otp") otp: String,
        @Header("latitude") latitude:String = "123.1241241",
        @Header("longitude") longitude:String = "124.124124",
    ): Flow<NetworkResponse<UserDto>>

    @GET("api/get-advertisment")
    fun getAdvertisements(): Flow<NetworkResponse<List<AdvertisementDto>>>

    @GET("api/get_public_notification")
    fun getNotifications(): Flow<NetworkResponse<List<NotificationDto>>>

    @GET("api/sliders-list")
    fun getSliderImage(): Flow<NetworkResponse<List<SliderImage>>>
}