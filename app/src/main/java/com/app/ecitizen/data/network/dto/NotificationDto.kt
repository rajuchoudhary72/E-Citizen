package com.app.ecitizen.data.network.dto
import com.google.gson.annotations.SerializedName


data class NotificationDto(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("notification_msg")
    val notificationMsg: String,
    @SerializedName("notification_msg_hi")
    val notificationMsgHi: String,
    @SerializedName("status")
    val status: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
)