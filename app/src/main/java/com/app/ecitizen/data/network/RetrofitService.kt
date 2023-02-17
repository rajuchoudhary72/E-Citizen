package com.app.ecitizen.data.network

import com.app.ecitizen.data.network.dto.AboutUsDto
import com.app.ecitizen.data.network.dto.AdvertisementDto
import com.app.ecitizen.data.network.dto.AppFront
import com.app.ecitizen.data.network.dto.Complaint
import com.app.ecitizen.data.network.dto.DownloadDto
import com.app.ecitizen.data.network.dto.NetworkResponse
import com.app.ecitizen.data.network.dto.NoticeDto
import com.app.ecitizen.data.network.dto.NotificationDto
import com.app.ecitizen.data.network.dto.OtpResponseDto
import com.app.ecitizen.data.network.dto.PhoneBookDto
import com.app.ecitizen.data.network.dto.PlaceDto
import com.app.ecitizen.data.network.dto.ServiceDto
import com.app.ecitizen.data.network.dto.SliderImage
import com.app.ecitizen.data.network.dto.UpdateProfileDto
import com.app.ecitizen.data.network.dto.UserDto
import com.app.ecitizen.data.network.dto.UserRegisterResponseDto
import com.app.ecitizen.features.place.ImportantPlaceUiState
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface RetrofitService {
    @GET("api/app-front")
    fun getAppFronts(): Flow<NetworkResponse<List<AppFront>>>

    @POST("api/otp-send")
    @FormUrlEncoded
    fun sendOtp(
        @Field("phone_no") mobileNumber: String
    ): Flow<OtpResponseDto>

    @POST("api/user-signup")
    @FormUrlEncoded
    fun createProfile(
        @Header("latitude") latitude:String = "123.1241241",
        @Header("longitude") longitude:String = "124.124124",
        @Field("mobile") mobile: String,
        @Field("name") name: String,
        @Field("ward") ward: String,
        @Field("colony") colony: String,
    ): Flow<UserRegisterResponseDto>

    @POST("api/user_profile_update")
    @FormUrlEncoded
    fun updateUserProfile(
        @Field("name") name: String,
        @Field("ward") ward: String,
        @Field("colony") colony: String,
    ): Flow<UpdateProfileDto>

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

    @GET("api/get_telephone_directory")
    fun getPhoneBook(@Query("search") search:String): Flow<NetworkResponse<List<PhoneBookDto>>>

    @GET("api/e-services")
    fun getServicesData(): Flow<NetworkResponse<List<ServiceDto>>>

    @GET("api/get_about_us")
    fun getAboutUs(@Query("search") search:String): Flow<NetworkResponse<List<AboutUsDto>>>

    @GET("api/complain_close")
    fun closeComplaint(@Query("id") id:String): Flow<NetworkResponse<List<String>>>

    @Multipart
    @POST("api/user_complain_update")
    fun registerComplaint(
        @Part("complain_heading") complainHeading: RequestBody,
        @Part("complain_sr_no") complainSrNo: RequestBody,
        @Part("house_no") house: RequestBody,
        @Part("colony_name") colony: RequestBody,
        @Part("street_name") street: RequestBody,
        @Part("notes") note: RequestBody,
        @Part("zone_no") zone: RequestBody,
        @Part("ward_no") ward: RequestBody,
        @Part file: MultipartBody.Part,
    ): Flow<NetworkResponse<String>>

    @GET("api/get_user_complain")
    fun getComplaints(): Flow<NetworkResponse<List<Complaint>>>

}