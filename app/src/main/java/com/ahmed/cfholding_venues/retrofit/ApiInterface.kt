package com.ahmed.cfholding_venues.retrofit

import com.ahmed.cfholding_venues.data.models.dto.VenuesResponse
import com.ahmed.cfholding_venues.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

    @GET(Constants.URL.GET_VENUES_LIST)
    suspend fun getVenuesList(
        @Query(Constants.QueryParams.LONG_LAT) latLong: String,
        @Query(Constants.QueryParams.CLIENT_SECRET) clientSecret: String = Constants.QueryDefaults.CLIENT_SECRET,
        @Query(Constants.QueryParams.CLIENT_ID) clientId: String= Constants.QueryDefaults.CLIENT_ID,
        @Query(Constants.QueryParams.V_SEARCH) vSearch: Long= Constants.QueryDefaults.V_SEARCH,
    ): VenuesResponse

}
