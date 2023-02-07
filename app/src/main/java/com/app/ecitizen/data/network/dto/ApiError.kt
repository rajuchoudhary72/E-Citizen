package com.app.ecitizen.data.network.dto

import com.google.gson.annotations.SerializedName


data class ApiError(
    @SerializedName("status")
    val status: Int,
    @SerializedName("Message")
    val message: String,
)