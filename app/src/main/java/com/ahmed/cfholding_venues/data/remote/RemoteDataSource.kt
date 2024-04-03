package com.ahmed.cfholding_venues.data.remote

import com.ahmed.cfholding_venues.data.models.dto.VenuesRequest
import com.ahmed.cfholding_venues.data.models.dto.VenuesResponse
import com.ahmed.cfholding_venues.retrofit.ApiInterface

class RemoteDataSource(private val mRetrofitInterface: ApiInterface) : IRemoteDataSource {
    override suspend fun getVenuesResponse(venuesRequest: VenuesRequest): VenuesResponse {
        return mRetrofitInterface.getVenuesList(venuesRequest.getLatLongParam())
    }
}