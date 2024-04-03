package com.ahmed.cfholding_venues.data.models.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Icon(
    @SerializedName("prefix")
    val prefix: String? = null,
    @SerializedName("suffix")
    val suffix: String? = null
)