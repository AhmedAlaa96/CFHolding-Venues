package com.ahmed.cfholding_venues.utils.utilities

import android.content.Context
import android.widget.Toast

object UIUtils {
    fun showToast(context: Context?, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }


}
