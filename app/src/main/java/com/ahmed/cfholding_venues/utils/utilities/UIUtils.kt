package com.ahmed.cfholding_venues.utils.utilities

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.widget.Toast

object UIUtils {
    fun showToast(context: Context?, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }


    fun showBasicDialog(
        context: Context, title: String? = null, message: String? = null,
        positiveButton: String, negativeButton: String? = null,
        positiveClickListener: DialogInterface.OnClickListener,
        negativeClickListener: DialogInterface.OnClickListener? = null,
        isCancelable: Boolean = true
    ): AlertDialog {
        return AlertDialog.Builder(context)
            .setTitle(title)
            .setCancelable(isCancelable)
            .setMessage(message)
            .setPositiveButton(positiveButton, positiveClickListener)
            .setNegativeButton(negativeButton, negativeClickListener)
            .show()
    }


}
