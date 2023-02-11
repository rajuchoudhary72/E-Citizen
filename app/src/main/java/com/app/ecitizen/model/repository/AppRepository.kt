package com.app.ecitizen.model.repository

import com.app.ecitizen.data.network.dto.AdvertisementDto
import com.app.ecitizen.data.network.dto.AppFront
import com.app.ecitizen.data.network.dto.NotificationDto
import com.app.ecitizen.data.network.dto.SliderImage
import com.app.ecitizen.data.network.dto.UserDto
import kotlinx.coroutines.flow.Flow

interface AppRepository {
     fun getAppFronts(): Flow<List<AppFront>>

    suspend fun sendOtp(mobileNumber:String): Flow<String>

    suspend fun verifyOtp(mobileNumber:String, otp:String): Flow<UserDto?>

     fun getAdvertisement(): Flow<List<AdvertisementDto>>

     fun getNotifications(): Flow<List<NotificationDto>>

     fun getSliderImages(): Flow<List<SliderImage>>
}