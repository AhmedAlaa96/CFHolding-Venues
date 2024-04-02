package com.ahmed.cfholding_venues.utils


fun String?.alternate(alt: String = Constants.General.DASH_TEXT): String {
    return if (!this?.trim().isNullOrEmpty()) this?.trim().toString() else alt
}

