package com.app.ecitizen.data.network.dto

import com.app.ecitizen.BuildConfig
import com.google.gson.annotations.SerializedName


data class PhoneBookDto(
    @SerializedName("base_path")
    val basePath: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("designation")
    val designation: String?,
    @SerializedName("file")
    val file: String?,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phone_no")
    val phoneNo: String?,
    @SerializedName("post_type")
    val postType: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
){

    fun fileUrl() = BuildConfig.SERVER_URL+basePath+file
}