package com.app.ecitizen.data

import com.app.ecitizen.data.datastore.ECitizenPreferencesDataStore
import com.app.ecitizen.data.network.RetrofitService
import com.app.ecitizen.data.network.dto.AdvertisementDto
import com.app.ecitizen.data.network.dto.AppFront
import com.app.ecitizen.data.network.dto.NotificationDto
import com.app.ecitizen.data.network.dto.SliderImage
import com.app.ecitizen.data.network.dto.UserDto
import com.app.ecitizen.model.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val retrofitService: RetrofitService,
    private val preferencesDataStore: ECitizenPreferencesDataStore
) : AppRepository {
    override fun getAppFronts(): Flow<List<AppFront>> {
        return retrofitService.getAppFronts().map { it.data ?: emptyList() }
    }

    override suspend fun sendOtp(mobileNumber: String): Flow<String> {
        return retrofitService.sendOtp(mobileNumber).map { it.message }
    }

    override suspend fun verifyOtp(mobileNumber: String, otp: String): Flow<UserDto?> {
        return retrofitService.verifyOtp(mobileNumber, otp).map { response ->
            if (response.data != null)
                preferencesDataStore.saveUserData(response.data)
            response.data
        }
    }

    override fun getAdvertisement(): Flow<List<AdvertisementDto>> {
        return retrofitService.getAdvertisements().map { it.data ?: emptyList() }
    }

    override fun getNotifications(): Flow<List<NotificationDto>> {
        return retrofitService.getNotifications().map { it.data ?: emptyList() }
    }

    override fun getSliderImages(): Flow<List<SliderImage>> {
        return retrofitService.getSliderImage().map { it.data?: emptyList() }
    }
}