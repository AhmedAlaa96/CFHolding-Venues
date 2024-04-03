package com.ahmed.cfholding_venues.data.remote

import com.ahmed.cfholding_venues.data.models.dto.VenuesRequest
import com.ahmed.cfholding_venues.data.models.dto.VenuesResponse

interface IRemoteDataSource {
    suspend fun getVenuesResponse(venuesRequest: VenuesRequest): VenuesResponse
}