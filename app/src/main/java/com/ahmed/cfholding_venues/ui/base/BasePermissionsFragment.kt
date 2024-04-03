package com.ahmed.cfholding_venues.ui.base

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.ahmed.cfholding_venues.R
import com.ahmed.cfholding_venues.utils.Constants
import com.ahmed.cfholding_venues.utils.utilities.UIUtils

abstract class BasePermissionsFragment<VB : ViewBinding> : BaseFragment<VB>() {

    private var mPermissionsTag: String? = null

    companion object {

        private const val MY_PERMISSIONS_REQUEST = 1
    }

    private var notGrantedPermissions = ArrayList<String>()

    abstract fun onPermissionGranted(permission: String)

    /**
     * Called right after the system permissions dialogs if the user checks "never ask again"
     *     on any of the requested permissions.
     */
    abstract fun onNeverAskAgainChecked(permission: String)

    abstract fun onPermissionDenied(permission: String)

    protected fun checkPermissions(
        context: Context, permissionTag: String? = null, rationaleDialogeMessage: String = "",
        vararg permissions: String
    ) {
        if (permissions.isEmpty())
            throw Exception("Check permission called without any permissions")
        mPermissionsTag = permissionTag
        notGrantedPermissions = ArrayList() // reset

        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                notGrantedPermissions.add(permission)
            }
        }

        if (notGrantedPermissions.isEmpty()) {
            onPermissionGranted(permissionTag ?: permissions[0])
        } else {
            if (shouldShowRationale(notGrantedPermissions)) {
                showCustomDialog(rationaleDialogeMessage)
            } else {
                requestPermissions(notGrantedPermissions.toTypedArray(), MY_PERMISSIONS_REQUEST)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Constants.RequestCodes.REQUEST_APP_SETTINGS) {
            var allPermissionsGranted = true

            for (permission in notGrantedPermissions) {
                if (!isPermissionGranted(permission)) {
                    allPermissionsGranted = false
                }
            }

            when {
                allPermissionsGranted -> onPermissionGranted(
                    mPermissionsTag
                        ?: notGrantedPermissions[0]
                )
                else -> onPermissionDenied(
                    mPermissionsTag
                        ?: notGrantedPermissions[0]
                )
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST -> {
                if (grantResults.isNotEmpty()) {
                    var allPermissionsGranted = true
                    var shouldShowRationale = true

                    for (grantResult in grantResults) {
                        if (grantResult == PackageManager.PERMISSION_DENIED) {
                            allPermissionsGranted = false
                            val showRationale = shouldShowRequestPermissionRationale(
                                permissions[grantResults.indexOf(grantResult)]
                            )
                            if (!showRationale) {
                                shouldShowRationale = false
                                break
                            }
                        }
                    }

                    when {
                        !shouldShowRationale -> onNeverAskAgainChecked(
                            mPermissionsTag
                                ?: permissions[0]
                        )
                        allPermissionsGranted -> onPermissionGranted(
                            mPermissionsTag
                                ?: permissions[0]
                        )
                        else -> onPermissionDenied(
                            mPermissionsTag
                                ?: permissions[0]
                        )
                    }
                }
            }
        }
    }

    private fun isPermissionGranted(permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(
            mContext,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun shouldShowRationale(permissions: ArrayList<String>): Boolean {
        var isNeed = false
        for (i in permissions.indices) {
            if (shouldShowRequestPermissionRationale(permissions[i])) {
                isNeed = true
                break
            }
        }
        return isNeed
    }

    private fun showCustomDialog(message: String) {
        UIUtils.showBasicDialog(mContext, null, message,
            getString(R.string.continue_text), getString(R.string.cancel),
            { _, _ ->
                requestPermissions(notGrantedPermissions.toTypedArray(), MY_PERMISSIONS_REQUEST)
            },
            { _, _ ->
                onPermissionDenied(mPermissionsTag ?: notGrantedPermissions[0])
            })
    }
}
