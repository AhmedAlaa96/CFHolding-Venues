package com.ahmed.cfholding_venues.data.repositories.home

import com.ahmed.cfholding_venues.data.local.ILocalDataSource
import com.ahmed.cfholding_venues.data.models.Status
import com.ahmed.cfholding_venues.data.models.dto.User
import com.ahmed.cfholding_venues.data.models.dto.VenuesRequest
import com.ahmed.cfholding_venues.data.models.dto.VenuesResponse
import com.ahmed.cfholding_venues.data.remote.IRemoteDataSource
import com.ahmed.cfholding_venues.data.sharedprefrences.IPreferencesDataSource
import com.ahmed.cfholding_venues.di.IoDispatcher
import com.ahmed.cfholding_venues.ui.base.BaseRepository
import com.ahmed.cfholding_venues.ui.base.IBaseRepository
import com.ahmed.cfholding_venues.utils.connection_utils.IConnectionUtils
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomeRepository @Inject constructor(
    connectionUtils: IConnectionUtils,
    private val mIRemoteDataSource: IRemoteDataSource,
    mILocalDataSource: ILocalDataSource,
    mIPreferencesDataSource: IPreferencesDataSource,
    mGson: Gson,
    @IoDispatcher override val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseRepository(connectionUtils, mIRemoteDataSource, mIPreferencesDataSource,mGson, dispatcher),
    IHomeRepository {
    override fun getVenuesResponse(venuesRequest: VenuesRequest): Flow<Status<VenuesResponse>> {
        return safeApiCalls {
            mIRemoteDataSource.getVenuesResponse(venuesRequest)
        }
    }
}