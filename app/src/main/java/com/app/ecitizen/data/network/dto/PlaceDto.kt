package com.app.ecitizen.data.network.dto
import com.google.gson.annotations.SerializedName


data class PlaceDto(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: String,
    @SerializedName("imp_place_lat")
    val impPlaceLat: String?,
    @SerializedName("imp_place_long")
    val impPlaceLong: String?,
    @SerializedName("place_name")
    val placeName: String?,
    @SerializedName("place_url")
    val placeUrl: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
)