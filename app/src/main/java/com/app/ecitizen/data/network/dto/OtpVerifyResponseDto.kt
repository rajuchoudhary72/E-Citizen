package com.app.ecitizen.data.network.dto
import com.google.gson.annotations.SerializedName


data class UserDto(
    @SerializedName("admin_image")
    val adminImage: Any?,
    @SerializedName("api_access_token")
    val apiAccessToken: String,
    @SerializedName("colony")
    val colony: String,
    @SerializedName("country_code")
    val countryCode: Any?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("otp")
    val otp: Any?,
    @SerializedName("status")
    val status: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_type")
    val userType: String,
    @SerializedName("ward")
    val ward: String
)