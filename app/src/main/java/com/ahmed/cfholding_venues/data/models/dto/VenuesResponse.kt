package com.ahmed.cfholding_venues.data.models.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class VenuesResponse(
    @SerializedName("meta")
    val meta: Meta? = null,
    @SerializedName("response")
    val response: Response? = null,

)