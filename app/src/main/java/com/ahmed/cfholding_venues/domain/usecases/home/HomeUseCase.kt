package com.ahmed.cfholding_venues.domain.usecases.home

import com.ahmed.cfholding_venues.data.models.Status
import com.ahmed.cfholding_venues.data.models.StatusCode
import com.ahmed.cfholding_venues.data.models.dto.Venue
import com.ahmed.cfholding_venues.data.models.dto.VenuesRequest
import com.ahmed.cfholding_venues.data.models.dto.VenuesResponse
import com.ahmed.cfholding_venues.data.repositories.home.IHomeRepository
import com.ahmed.cfholding_venues.data.repositories.login.ILoginRepository
import com.ahmed.cfholding_venues.ui.base.BaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeUseCase @Inject constructor(private val repository: IHomeRepository) :
    BaseUseCase(repository), IHomeUseCase {
    override fun getVenuesResponse(venuesRequest: VenuesRequest): Flow<Status<ArrayList<Venue>>> {
        return repository.getVenuesResponse(venuesRequest)
            .map(::mapGetVenuesResponse)
    }


    private fun mapGetVenuesResponse(status: Status<VenuesResponse>): Status<ArrayList<Venue>> {
        return when (validateResponse(status)) {
            StatusCode.VALID -> {
                Status.Success(status.data?.response?.venues)
            }

            else -> {
                Status.CopyStatus(status, null)
            }
        }
    }

}