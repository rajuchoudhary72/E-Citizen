package com.app.ecitizen.data.network.dto

import com.app.ecitizen.BuildConfig
import com.google.gson.annotations.SerializedName


data class DownloadDto(
    @SerializedName("base_path")
    val basePath: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("file")
    val file: String?,
    @SerializedName("id")
    val id: String,
    @SerializedName("status")
    val status: String?,
    @SerializedName("subject")
    val subject: String?,
    @SerializedName("subject_hi")
    val subjectHi: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
){
    fun fileUrl() = BuildConfig.SERVER_URL+basePath+file
}