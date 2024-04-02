package com.ahmed.cfholding_venues.data.models.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Location(
    @SerializedName("address")
    val address: String? = null,
    @SerializedName("cc")
    val cc: String? = null,
    @SerializedName("city")
    val city: String? = null,
    @SerializedName("country")
    val country: String? = null,
    @SerializedName("crossStreet")
    val crossStreet: String? = null,
    @SerializedName("distance")
    val distance: Int? = null,
    @SerializedName("formattedAddress")
    val formattedAddress: List<String?>? = null,
    @SerializedName("labeledLatLngs")
    val labeledLatLngs: List<LabeledLatLng?>? = null,
    @SerializedName("lat")
    val lat: Double? = null,
    @SerializedName("lng")
    val lng: Double? = null,
    @SerializedName("postalCode")
    val postalCode: String? = null,
    @SerializedName("state")
    val state: String? = null
)