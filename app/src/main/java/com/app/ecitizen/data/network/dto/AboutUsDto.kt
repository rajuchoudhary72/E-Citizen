package com.app.ecitizen.data.network.dto
import com.app.ecitizen.BuildConfig
import com.google.gson.annotations.SerializedName


data class AboutUsDto(
    @SerializedName("base_path")
    val basePath: String? = null ,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("designation")
    val designation: String? = null,
    @SerializedName("file")
    val file: String? = null,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("phone_no")
    val phoneNo: Any? = null,
    @SerializedName("post_type")
    val postType: String? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null
){
    fun fileUrl() = BuildConfig.SERVER_URL+basePath+file
}