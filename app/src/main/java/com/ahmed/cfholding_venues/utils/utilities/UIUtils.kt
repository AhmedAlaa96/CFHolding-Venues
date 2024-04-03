package com.ahmed.cfholding_venues.utils.utilities

import android.app.ActionBar.LayoutParams
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.ahmed.cfholding_venues.R
import com.ahmed.cfholding_venues.data.models.dto.Venue
import com.ahmed.cfholding_venues.utils.Constants
import com.ahmed.cfholding_venues.utils.alternate
import com.ahmed.cfholding_venues.utils.setNetworkImage
import com.google.android.gms.maps.model.Marker

object UIUtils {
    fun showToast(context: Context?, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }


    fun showBasicDialog(
        context: Context,
        title: String? = null,
        message: String? = null,
        positiveButton: String,
        negativeButton: String? = null,
        positiveClickListener: DialogInterface.OnClickListener,
        negativeClickListener: DialogInterface.OnClickListener? = null,
        isCancelable: Boolean = true
    ): AlertDialog {
        return AlertDialog.Builder(context).setTitle(title).setCancelable(isCancelable)
            .setMessage(message).setPositiveButton(positiveButton, positiveClickListener)
            .setNegativeButton(negativeButton, negativeClickListener).show()
    }

    fun showMarkerDialog(mContext: Context, marker: Marker) {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_venue)

        // set the custom dialog components - text, image and button

        // set the custom dialog components - text, image and button
        val txtName = dialog.findViewById<View>(R.id.txtName) as TextView
        val txtLocation = dialog.findViewById<View>(R.id.txtLocation) as TextView
        val txtCategory = dialog.findViewById<View>(R.id.txtCategory) as TextView
        val dialogButton = dialog.findViewById<View>(R.id.btnClose) as ImageButton
        val imgCategory = dialog.findViewById<View>(R.id.imgCategory) as ImageView

        val (location, categoryText, imagePath) = marker.snippet?.split("\n") ?: listOf(
            "-", "-", "-"
        )

        txtName.text = marker.title
        txtLocation.text = location.alternate()
        txtCategory.text = categoryText.alternate()
        if (imagePath == Constants.General.DASH_TEXT) {
            imgCategory.setImageResource(R.drawable.ic_error)
        } else {
            imgCategory.setNetworkImage(imagePath)
        }

        dialogButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
    
}
