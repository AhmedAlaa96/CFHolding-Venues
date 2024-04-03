package com.ahmed.cfholding_venues.ui.home

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
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
import com.ahmed.cfholding_venues.utils.Utils
import com.ahmed.cfholding_venues.utils.alternate
import com.ahmed.cfholding_venues.utils.observe
import com.ahmed.cfholding_venues.utils.utilities.UIUtils
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
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
        showNeedPermission()
    }

    override fun onPermissionDenied(permission: String) {
        showNeedPermission()
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
        getLocationPermissionAndFetchData()
    }

    private fun getLocationPermissionAndFetchData() {
        binding.errorLayout.root.isVisible = false
        binding.permissionLayout.root.isVisible = false
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

    private fun initMaps(venuesList: ArrayList<Venue>) {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(viewModel.getMapCallback(mContext, venuesList))
    }

    override fun setListeners() {
        binding.btnChangeView.setOnClickListener {
            changeLayoutView()
        }
        binding.errorLayout.btnRetry.setOnClickListener {
            getLocationPermissionAndFetchData()
        }
        binding.permissionLayout.btnRetry.setOnClickListener {
            openAppSettings()
        }
    }

    private fun changeLayoutView() {
        viewModel.changeLayoutView()
    }

    override fun bindViewModels() {
        bindLoadingObserver()
        bindErrorObserver()
        bindToastMessageObserver()
        bindVenuesListObserver()
        bindLayoutViewObserver()
    }

    private fun bindLayoutViewObserver() {
        observe(viewModel.layoutViewMutableSharedFlow) { isListView ->
            if (isListView) {
                binding.btnChangeView.setImageResource(R.drawable.ic_list_view)
                binding.txtChangeVeiwLabel.text = getString(R.string.list_view)
                binding.rvVenues.isVisible = true
                binding.mapContainer.isVisible = false
            } else {
                binding.btnChangeView.setImageResource(R.drawable.ic_map_view)
                binding.txtChangeVeiwLabel.text = getString(R.string.map_view)
                binding.rvVenues.isVisible = false
                binding.mapContainer.isVisible = true
            }
        }
    }

    private fun bindVenuesListObserver() {
        observe(viewModel.venuesResponseMutableSharedFlow) { status ->
            when (status.statusCode) {
                StatusCode.SUCCESS -> {
                    binding.constraintContent.isVisible = true
                    binding.errorLayout.root.isVisible = false
                    binding.permissionLayout.root.isVisible = false
                    status.data?.let {
                        initMaps(it)
                        venuesAdapter.addItems(it)
                    } ?: showNoDataErrorLayout()
                }

                else -> {
                    onVenuesListFailed(status.error)
                }
            }
        }
    }

    private fun showNoDataErrorLayout() {
        with(binding) {
            constraintContent.isVisible = false
            errorLayout.root.isVisible = true
            errorLayout.txtError.text = getString(R.string.no_data)
            errorLayout.btnRetry.isVisible = false
        }
    }

    private fun showNeedPermission() {
        with(binding) {
            constraintContent.isVisible = false
            permissionLayout.root.isVisible = true
            permissionLayout.txtError.text = getString(R.string.need_location_permission)
            permissionLayout.btnRetry.isVisible = true
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.RequestCodes.PERMISSION_REQUEST_CODE) {
            getLocationPermissionAndFetchData()
        }
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", Utils.getPackageName(), null)
        intent.data = uri
        startActivityForResult(intent, Constants.RequestCodes.PERMISSION_REQUEST_CODE)
    }


}