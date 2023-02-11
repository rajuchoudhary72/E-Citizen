package com.app.ecitizen.data.network.dto

import com.app.ecitizen.BuildConfig
import com.google.gson.annotations.SerializedName


data class NoticeDto(
    @SerializedName("base_path")
    val basePath: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("file")
    val file: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("message")
    val message: String?,
    @SerializedName("notes")
    val notes: Any?,
    @SerializedName("notification_type")
    val notificationType: String?,
    @SerializedName("updated_at")
    val updatedAt: String
){
    fun fileUrl() = BuildConfig.SERVER_URL+basePath+file
}