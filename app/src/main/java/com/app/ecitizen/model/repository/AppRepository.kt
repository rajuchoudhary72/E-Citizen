package com.app.ecitizen.model.repository

import com.app.ecitizen.data.network.dto.AboutUsDto
import com.app.ecitizen.data.network.dto.AdvertisementDto
import com.app.ecitizen.data.network.dto.AppFront
import com.app.ecitizen.data.network.dto.DownloadDto
import com.app.ecitizen.data.network.dto.NoticeDto
import com.app.ecitizen.data.network.dto.NotificationDto
import com.app.ecitizen.data.network.dto.PhoneBookDto
import com.app.ecitizen.data.network.dto.PlaceDto
import com.app.ecitizen.data.network.dto.ServiceDto
import com.app.ecitizen.data.network.dto.SliderImage
import com.app.ecitizen.data.network.dto.UserDto
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun getAppFronts(): Flow<List<AppFront>>

    suspend fun sendOtp(mobileNumber: String): Flow<String>

    suspend fun crateUserProfile(
        mobileNumber: String,
        name: String,
        ward: String,
        colony: String,
    ): Flow<String>

    suspend fun updateUserProfile(
        name: String,
        ward: String,
        colony: String,
    ): Flow<String>

    suspend fun verifyOtp(mobileNumber: String, otp: String): Flow<UserDto?>

    fun getAdvertisement(): Flow<List<AdvertisementDto>>

    fun getNotifications(): Flow<List<NotificationDto>>

    fun getSliderImages(): Flow<List<SliderImage>>

    fun getNotices(type: String): Flow<List<NoticeDto>>

    fun getDownloads(): Flow<List<DownloadDto>>

    fun getImportantPlaces(search: String): Flow<List<PlaceDto>>

    fun getPhoneBook(search: String): Flow<List<PhoneBookDto>>

    fun getServicesData(): Flow<ServiceDto>

    fun getContactUs(type: String): Flow<List<AboutUsDto>>
}