package com.app.ecitizen.data.network.dto
import com.google.gson.annotations.SerializedName


data class UserRegisterResponseDto(
    @SerializedName("Authorization")
    val authorization: String?,
    @SerializedName("data")
    val data: User?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?
)

data class User(
    @SerializedName("aadhar_no")
    val aadharNo: Any?,
    @SerializedName("cat_ids")
    val catIds: Any?,
    @SerializedName("category")
    val category: Any?,
    @SerializedName("colony")
    val colony: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("fname")
    val fname: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("image")
    val image: Any?,
    @SerializedName("lname")
    val lname: Any?,
    @SerializedName("password")
    val password: Any?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("pincode")
    val pincode: Any?,
    @SerializedName("state")
    val state: Any?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("user_id")
    val userId: String?,
    @SerializedName("user_type")
    val userType: String?,
    @SerializedName("ward")
    val ward: String?,
    @SerializedName("zone")
    val zone: Any?
)