package com.app.ecitizen.data.network.dto

import com.google.gson.annotations.SerializedName


data class AppFront(
    @SerializedName("base_path")
    val basePath: String,
    @SerializedName("buttion_1_name")
    val buttion1Name: String,
    @SerializedName("buttion_1_name_hi")
    val buttion1NameHi: String,
    @SerializedName("buttion_2_name")
    val buttion2Name: String,
    @SerializedName("buttion_2_name_hi")
    val buttion2NameHi: String,
    @SerializedName("buttion_3_name")
    val buttion3Name: String,
    @SerializedName("buttion_3_name_hi")
    val buttion3NameHi: Any?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("logo")
    val logo: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("update_at")
    val updateAt: String
)