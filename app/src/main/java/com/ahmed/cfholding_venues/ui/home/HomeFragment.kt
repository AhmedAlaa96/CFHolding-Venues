package com.ahmed.cfholding_venues.ui.home

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahmed.cfholding_venues.R
import com.ahmed.cfholding_venues.databinding.FragmentHomeBinding
import com.ahmed.cfholding_venues.ui.base.BaseFragment
import com.ahmed.cfholding_venues.ui.base.BasePermissionsFragment
import com.ahmed.cfholding_venues.utils.Constants

class HomeFragment : BasePermissionsFragment<FragmentHomeBinding>() {
    override fun onPermissionGranted(permission: String) {
        // TODO:: VIEW MODEL CALL
    }

    override fun onNeverAskAgainChecked(permission: String) {
    }

    override fun onPermissionDenied(permission: String) {

    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun onActivityReady(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            checkPermissions(
                mContext,
                Constants.PermissionTag.LOCATION_PERMISSION,
                rationaleDialogeMessage = getString(R.string.location_permission),
                permissions = arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION

                ),
            )
        } else {
            checkPermissions(
                mContext,
                Constants.PermissionTag.LOCATION_PERMISSION,
                rationaleDialogeMessage = getString(R.string.location_permission),
                permissions = arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
            )
        }
    }

    override fun initViews() {
    }

    override fun setListeners() {
    }

    override fun bindViewModels() {
    }

    override fun showError(shouldShow: Boolean) {
    }

}