package com.app.ecitizen.data.network.dto
import com.google.gson.annotations.SerializedName


data class UserDto(
    @SerializedName("admin_image")
    val adminImage: Any?=null,
    @SerializedName("api_access_token")
    val apiAccessToken: String,
    @SerializedName("colony")
    val colony: String,
    @SerializedName("country_code")
    val countryCode: Any?=null,
    @SerializedName("created_at")
    val createdAt: String?=null,
    @SerializedName("email")
    val email: String?=null,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: String?=null,
    @SerializedName("id")
    val id: Int?=null,
    @SerializedName("latitude")
    val latitude: String?=null,
    @SerializedName("longitude")
    val longitude: String?=null,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("otp")
    val otp: String?=null,
    @SerializedName("status")
    val status: String?=null,
    @SerializedName("updated_at")
    val updatedAt: String?=null,
    @SerializedName("user_type")
    val userType: String?=null,
    @SerializedName("ward")
    val ward: String
)