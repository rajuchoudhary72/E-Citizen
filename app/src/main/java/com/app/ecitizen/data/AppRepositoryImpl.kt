package com.app.ecitizen.data

import android.content.Context
import com.app.ecitizen.data.datastore.ECitizenPreferencesDataStore
import com.app.ecitizen.data.network.RetrofitService
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
import com.app.ecitizen.model.repository.AppRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val retrofitService: RetrofitService,
    private val preferencesDataStore: ECitizenPreferencesDataStore
) : AppRepository {
    override fun getAppFronts(): Flow<List<AppFront>> {
        return retrofitService.getAppFronts().map { it.data ?: emptyList() }
    }

    override suspend fun sendOtp(mobileNumber: String): Flow<String> {
        return retrofitService.sendOtp(mobileNumber).map { it.message }
    }

    override suspend fun crateUserProfile(
        mobileNumber: String,
        name: String,
        ward: String,
        colony: String
    ): Flow<String> {
        return retrofitService.createProfile(
            mobile = mobileNumber,
            name = name,
            ward = ward,
            colony = colony,
        ).map {
            preferencesDataStore.saveUserData(
                UserDto(
                    name = it.data?.fname!!,
                    ward = it.data.ward!!,
                    colony = it.data.colony!!,
                    mobile = it.data.phone!!,
                    apiAccessToken = it.authorization!!
                )
            )
            it.message!!
        }
    }

    override suspend fun updateUserProfile(
        name: String,
        ward: String,
        colony: String
    ): Flow<String> {
        return retrofitService.updateUserProfile(
            name = name,
            ward = ward,
            colony = colony,
        ).map {

            val currentUser = preferencesDataStore.getUserDtoFlow().first()

            preferencesDataStore.saveUserData(
                UserDto(
                    name = it.data?.fname!!,
                    ward = it.data.ward!!,
                    colony = it.data.colony!!,
                    mobile = it.data.phone!!,
                    apiAccessToken = currentUser?.apiAccessToken!!
                )
            )
            it.message!!

        }
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
        return retrofitService.getSliderImage().map { it.data ?: emptyList() }
    }

    override fun getNotices(type: String): Flow<List<NoticeDto>> {
        return retrofitService.getNotice(type).map { it.data ?: emptyList() }
    }

    override fun getDownloads(): Flow<List<DownloadDto>> {
        return retrofitService.getDownloads().map { it.data ?: emptyList() }
    }

    override fun getImportantPlaces(search: String): Flow<List<PlaceDto>> {
        return retrofitService.getImportantPlaces(search).map { it.data ?: emptyList() }
    }

    override fun getPhoneBook(search: String): Flow<List<PhoneBookDto>> {
        return retrofitService.getPhoneBook(search).map { it.data ?: emptyList() }
    }

    override fun getServicesData(): Flow<ServiceDto> {
        return retrofitService.getServicesData().map { it.data?.first()!! }
    }

    override fun getContactUs(type: String): Flow<List<AboutUsDto>> {
        return retrofitService.getAboutUs(type).map { it.data?: emptyList() }
    }
}