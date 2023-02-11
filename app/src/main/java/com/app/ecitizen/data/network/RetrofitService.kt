package com.app.ecitizen.data.network

import com.app.ecitizen.data.network.dto.AdvertisementDto
import com.app.ecitizen.data.network.dto.AppFront
import com.app.ecitizen.data.network.dto.DownloadDto
import com.app.ecitizen.data.network.dto.NetworkResponse
import com.app.ecitizen.data.network.dto.NoticeDto
import com.app.ecitizen.data.network.dto.NotificationDto
import com.app.ecitizen.data.network.dto.OtpResponseDto
import com.app.ecitizen.data.network.dto.PlaceDto
import com.app.ecitizen.data.network.dto.ServiceDto
import com.app.ecitizen.data.network.dto.SliderImage
import com.app.ecitizen.data.network.dto.UserDto
import com.app.ecitizen.features.place.ImportantPlaceUiState
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

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

    @GET("api/notification")
    fun getNotice(@Query("search") noticeType:String): Flow<NetworkResponse<List<NoticeDto>>>

    @GET("api/download_data")
    fun getDownloads(): Flow<NetworkResponse<List<DownloadDto>>>

    @GET("api/get_importantplace_list")
    fun getImportantPlaces(@Query("search") search:String): Flow<NetworkResponse<List<PlaceDto>>>

    @GET("api/e-services")
    fun getServicesData(): Flow<NetworkResponse<List<ServiceDto>>>
}