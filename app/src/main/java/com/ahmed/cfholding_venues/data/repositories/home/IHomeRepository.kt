package com.ahmed.cfholding_venues.data.repositories.home

import com.ahmed.cfholding_venues.data.models.Status
import com.ahmed.cfholding_venues.data.models.dto.User
import com.ahmed.cfholding_venues.data.models.dto.VenuesRequest
import com.ahmed.cfholding_venues.data.models.dto.VenuesResponse
import com.ahmed.cfholding_venues.ui.base.IBaseRepository
import kotlinx.coroutines.flow.Flow

interface IHomeRepository: IBaseRepository {
    fun getVenuesResponse(venuesRequest: VenuesRequest): Flow<Status<VenuesResponse>>
}