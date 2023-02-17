package com.app.ecitizen.data.network.dto
import com.app.ecitizen.BuildConfig
import com.google.gson.annotations.SerializedName


data class Complaint(
    @SerializedName("colony")
    val colony: String?,
    @SerializedName("colony_name")
    val colonyName: String?,
    @SerializedName("complain_file")
    val complainFile: String?,
    @SerializedName("complain_id")
    val complainId: String,
    @SerializedName("complain_row_id")
    val complainRowId: Any?,
    @SerializedName("complain_type")
    val complainType: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("file_path")
    val filePath: String?,
    @SerializedName("fname")
    val fname: String?,
    @SerializedName("house_no")
    val houseNo: String?,
    @SerializedName("id")
    val id: String,
    @SerializedName("lname")
    val lname: Any?,
    @SerializedName("notes")
    val notes: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("status")
    val status: String?,
    @SerializedName("street_name")
    val streetName: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("user_id")
    val userId: String?,
    @SerializedName("ward")
    val ward: String?,
    @SerializedName("ward_no")
    val wardNo: String?,
    @SerializedName("zone_no")
    val zoneNo: String?
){
    fun fileUrl() = BuildConfig.SERVER_URL+filePath+complainFile
}