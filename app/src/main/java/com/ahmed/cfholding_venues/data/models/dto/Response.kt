package com.ahmed.cfholding_venues.data.models.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Response(
    @SerializedName("confident")
    val confident: Boolean? = null,
    @SerializedName("venues")
    val venues: ArrayList<Venue>? = null
)