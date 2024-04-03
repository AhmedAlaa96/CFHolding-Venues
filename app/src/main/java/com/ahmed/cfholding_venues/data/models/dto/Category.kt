package com.ahmed.cfholding_venues.data.models.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Category(
    @SerializedName("categoryCode")
    val categoryCode: Int? = null,
    @SerializedName("icon")
    val icon: Icon? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("mapIcon")
    val mapIcon: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("pluralName")
    val pluralName: String? = null,
    @SerializedName("primary")
    val primary: Boolean? = null,
    @SerializedName("shortName")
    val shortName: String? = null
)