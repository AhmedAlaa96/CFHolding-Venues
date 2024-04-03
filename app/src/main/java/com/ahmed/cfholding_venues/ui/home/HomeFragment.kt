package com.ahmed.cfholding_venues.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmed.cfholding_venues.R
import com.ahmed.cfholding_venues.data.models.LoadingModel
import com.ahmed.cfholding_venues.data.models.ProgressTypes
import com.ahmed.cfholding_venues.data.models.StatusCode
import com.ahmed.cfholding_venues.data.models.dto.Venue
import com.ahmed.cfholding_venues.data.models.dto.VenuesRequest
import com.ahmed.cfholding_venues.databinding.FragmentHomeBinding
import com.ahmed.cfholding_venues.ui.base.BasePermissionsFragment
import com.ahmed.cfholding_venues.ui.base.ListItemClickListener
import com.ahmed.cfholding_venues.ui.home.adapter.VenuesAdapter
import com.ahmed.cfholding_venues.utils.Constants
import com.ahmed.cfholding_venues.utils.alternate
import com.ahmed.cfholding_venues.utils.observe
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BasePermissionsFragment<FragmentHomeBinding>(), ListItemClickListener<Venue> {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var mVenuesLayoutManager: LinearLayoutManager
    private lateinit var venuesAdapter: VenuesAdapter
    override fun onPermissionGranted(permission: String) {
        getLocation()
    }

    private fun getLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        if (ActivityCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    // Use the location
                    val latitude = location.latitude
                    val longitude = location.longitude
                    viewModel.getVenuesListResponse(VenuesRequest(latitude, longitude))
                }
            }
    }

    override fun onNeverAskAgainChecked(permission: String) {
    }

    override fun onPermissionDenied(permission: String) {

    }

    private fun initAdapter() {
        venuesAdapter = VenuesAdapter(mContext, this)
        mVenuesLayoutManager = LinearLayoutManager(mContext)
        binding.rvVenues.layoutManager = mVenuesLayoutManager
        binding.rvVenues.adapter = venuesAdapter
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun onActivityReady(savedInstanceState: Bundle?) {
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

    override fun initViews() {
        initAdapter()
    }

    override fun setListeners() {
    }

    override fun bindViewModels() {
        bindLoadingObserver()
        bindErrorObserver()
        bindToastMessageObserver()
        bindVenuesListObserver()
    }

    private fun bindVenuesListObserver() {
        observe(viewModel.venuesResponseMutableSharedFlow) {
            when (it.statusCode) {
                StatusCode.SUCCESS -> {
                    binding.constraintContent.isVisible = true
                    binding.errorLayout.root.isVisible = false
                    venuesAdapter.addItems(it.data!!)
                }
                else -> {
                    onVenuesListFailed(it.error)
                }
            }
        }
    }

    private fun onVenuesListFailed(error: String?) {
        binding.constraintContent.isVisible = false
        binding.errorLayout.root.isVisible = true
        binding.errorLayout.txtError.text =
            error.alternate(getString(R.string.some_thing_went_wrong))
        binding.errorLayout.btnRetry.isVisible = true
    }

    override fun showError(shouldShow: Boolean) {
    }

    private fun bindLoadingObserver() {
        observe(viewModel.loadingObservable) {
            onLoadingObserverRetrieved(it)
        }
    }


    private fun onLoadingObserverRetrieved(loadingModel: LoadingModel) {
        loadingModel.loadingProgressView = binding.viewProgress.loadingIndicator
        loadingModel.loadingFullProgressView = binding.viewFullProgress.root
        binding.viewProgress.root.isVisible =
            (loadingModel.shouldShow && loadingModel.progressType == ProgressTypes.MAIN_PROGRESS)

        binding.viewFullProgress.root.isVisible =
            (loadingModel.shouldShow && loadingModel.progressType == ProgressTypes.FULL_PROGRESS)
    }

    private fun bindErrorObserver() {
        observe(viewModel.errorViewObservable) {
            showError(it)
            binding.errorLayout.root.visibility = View.GONE
        }
    }

    private fun bindToastMessageObserver() {
        observe(viewModel.showToastObservable) {
            showMessage(it)
        }
    }

    override fun onItemClick(item: Venue, position: Int) {
    }


}