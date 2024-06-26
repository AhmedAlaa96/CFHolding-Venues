package com.ahmed.cfholding_venues.ui.home

import android.content.Context
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ahmed.cfholding_venues.R
import com.ahmed.cfholding_venues.data.models.ProgressTypes
import com.ahmed.cfholding_venues.data.models.Status
import com.ahmed.cfholding_venues.data.models.dto.Category
import com.ahmed.cfholding_venues.data.models.dto.Venue
import com.ahmed.cfholding_venues.data.models.dto.VenuesRequest
import com.ahmed.cfholding_venues.di.MainDispatcher
import com.ahmed.cfholding_venues.domain.usecases.home.IHomeUseCase
import com.ahmed.cfholding_venues.ui.base.BaseViewModel
import com.ahmed.cfholding_venues.utils.Utils
import com.ahmed.cfholding_venues.utils.alternate
import com.ahmed.cfholding_venues.utils.utilities.UIUtils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    handle: SavedStateHandle,
    private val mUseCase: IHomeUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
) : BaseViewModel(handle, mUseCase) {
    private var venuesResponseStatus: Status<ArrayList<Venue>>? = null


    private val _venuesResponseMutableSharedFlow = MutableSharedFlow<Status<ArrayList<Venue>>>()
    val venuesResponseMutableSharedFlow = _venuesResponseMutableSharedFlow.asSharedFlow()

    private val _layoutViewMutableSharedFlow = MutableStateFlow(true)
    val layoutViewMutableSharedFlow = _layoutViewMutableSharedFlow.asStateFlow()

    private var venuesRequest: VenuesRequest? = null

    fun getVenuesListResponse(venuesRequest: VenuesRequest) {
        this.venuesRequest = venuesRequest
        val handler = CoroutineExceptionHandler { _, exception ->
            viewModelScope.launch {
                setVenuesResponseStatus(Status.Error(error = exception.message))
            }
        }

        viewModelScope.launch(mainDispatcher + handler) {
            if (venuesResponseStatus != null && venuesResponseStatus?.isIdle() != true && venuesResponseStatus?.isSuccess() == true) {

                setVenuesResponseStatus(venuesResponseStatus!!)
            } else {
                callGetVenuesList(ProgressTypes.MAIN_PROGRESS, venuesRequest)
            }
        }
    }

    private suspend fun callGetVenuesList(
        progressType: ProgressTypes,
        venuesRequest: VenuesRequest
    ) {
        onGetVenuesSubscribe(progressType)
        mUseCase.getVenuesResponse(venuesRequest)
            .onStart {
                showProgress(true, progressType)
            }.onCompletion {
                showProgress(false, progressType)
            }.catch {
                setVenuesResponseStatus(Status.Error(error = it.message))
                showProgress(false, progressType)
            }
            .collect {
                setVenuesResponseStatus(it)
            }
    }

    private fun onGetVenuesSubscribe(progressType: ProgressTypes) {
        showProgress(true, progressType)
        shouldShowError(false)
    }

    private suspend fun setVenuesResponseStatus(venuesResponseStatus: Status<ArrayList<Venue>>) {
        if (venuesResponseStatus.isSuccess()) {
            val venues = venuesResponseStatus.data
            this.venuesResponseStatus = Status.Success(venues)
        } else {
            this.venuesResponseStatus = venuesResponseStatus
        }

        _venuesResponseMutableSharedFlow.emit(venuesResponseStatus)
    }

    fun changeLayoutView() {
        viewModelScope.launch {
            _layoutViewMutableSharedFlow.emit(!_layoutViewMutableSharedFlow.value)
        }
    }

    fun getMapCallback(mContext: Context, venuesList: ArrayList<Venue>) =
        OnMapReadyCallback { googleMap ->
            val currentLocation =
                LatLng(venuesRequest?.latitude ?: -34.0, venuesRequest?.longitude ?: 151.0)
            googleMap.addMarker(MarkerOptions().position(currentLocation).title("My location"))
            venuesList.forEach { venue ->
                val tempLocation = LatLng(venue.location?.lat ?: 0.0, venue.location?.lng ?: 0.0)
                val drawable =
                    ResourcesCompat.getDrawable(mContext.resources, R.drawable.ic_marker, null)
                val markerIcon = Utils.drawableToBitmapDescriptor(drawable!!)
                googleMap.addMarker(
                    MarkerOptions()
                        .position(tempLocation)
                        .title(venue.name)
                        .snippet(generateSnippetText(mContext, venue))
                        .icon(markerIcon)
                )
            }
            googleMap.setOnMarkerClickListener(getOnMarkerClicked(mContext))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 17.5f))
        }


    private fun generateSnippetText(mContext: Context, venue: Venue): String {
        return "${venue.location?.formattedAddress?.joinToString { it }}\n${
            mContext.getString(
                R.string.category,
                venue.categories?.firstOrNull()?.name.alternate(
                    "-"
                )
            )
        }\n${getIconPath(venue.categories?.firstOrNull())}"
    }

    private fun getIconPath(category: Category?): String {
        return if (category == null) "-"
        else {
            "${category.icon?.prefix}64${category.icon?.suffix}"
        }
    }

    private fun getOnMarkerClicked(mContext: Context) = OnMarkerClickListener { marker ->
        UIUtils.showMarkerDialog(mContext, marker)
        true
    }

    fun logout() {
        mUseCase.logout()
    }

}