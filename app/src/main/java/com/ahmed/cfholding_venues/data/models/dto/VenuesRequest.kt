package com.ahmed.cfholding_venues.data.models.dto

import android.os.Parcelable
import androidx.annotation.Keep
import com.ahmed.cfholding_venues.utils.Constants
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class VenuesRequest(
    val latitude: Double? = null,
    val longitude: Double? = null
) : Parcelable {

    fun getLatLongParam(): String {
        return if (!latitude?.toString().isNullOrEmpty() && !longitude?.toString().isNullOrEmpty())
            "$latitude, $longitude"
        else Constants.General.EMPTY_TEXT
    }

}
