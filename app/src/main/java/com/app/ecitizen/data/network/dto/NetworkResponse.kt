package com.app.ecitizen.data.network.dto

import com.google.gson.annotations.SerializedName

data class NetworkResponse<T>(
    @SerializedName("data")
    val data: T?,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)
