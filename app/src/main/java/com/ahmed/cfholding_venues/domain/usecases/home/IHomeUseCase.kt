package com.ahmed.cfholding_venues.domain.usecases.home

import com.ahmed.cfholding_venues.data.models.Status
import com.ahmed.cfholding_venues.data.models.dto.Venue
import com.ahmed.cfholding_venues.data.models.dto.VenuesRequest
import com.ahmed.cfholding_venues.data.models.dto.VenuesResponse
import com.ahmed.cfholding_venues.ui.base.IBaseUseCase
import kotlinx.coroutines.flow.Flow

interface IHomeUseCase: IBaseUseCase {
    fun getVenuesResponse(venuesRequest: VenuesRequest): Flow<Status<ArrayList<Venue>>>
    fun logout()
}