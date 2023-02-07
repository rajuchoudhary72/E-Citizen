package com.app.ecitizen.data.network.dto

import com.google.gson.annotations.SerializedName


data class OtpResponseDto(
    @SerializedName("message")
    val message: String,
    @SerializedName("otp")
    val otp: Int,
    @SerializedName("status")
    val status: Int
)