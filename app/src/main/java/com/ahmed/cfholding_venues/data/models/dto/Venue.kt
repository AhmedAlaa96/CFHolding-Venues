package com.ahmed.cfholding_venues.data.models.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Venue(
    @SerializedName("categories")
    val categories: ArrayList<Category>? = null,
    @SerializedName("createdAt")
    val createdAt: Int? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("location")
    val location: Location? = null,
    @SerializedName("name")
    val name: String? = null
)