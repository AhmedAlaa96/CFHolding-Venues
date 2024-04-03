package com.ahmed.cfholding_venues.data.models.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Meta(
    @SerializedName("code")
    val code: Int? = null,
    @SerializedName("requestId")
    val requestId: String? = null,
    @SerializedName("errorType")
    val errorType: String? = null,
    @SerializedName("errorDetail")
    val errorDetail: String? = null,
)