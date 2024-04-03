package com.ahmed.cfholding_venues.data.repositories.splash

import com.ahmed.cfholding_venues.data.local.ILocalDataSource
import com.ahmed.cfholding_venues.data.models.Status
import com.ahmed.cfholding_venues.data.models.dto.User
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

class SplashRepository @Inject constructor(
    connectionUtils: IConnectionUtils,
    mIRemoteDataSource: IRemoteDataSource,
    private val mILocalDataSource: ILocalDataSource,
    private val mIPreferencesDataSource: IPreferencesDataSource,
    @IoDispatcher val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseRepository(connectionUtils, mIRemoteDataSource, mIPreferencesDataSource, dispatcher),
    ISplashRepository {
    override fun getUserData(): Flow<Status<User>> {
        return flow<Status<User>> {
            emit(Status.Success(mIPreferencesDataSource.getUserData()))
        }.flowOn(dispatcher)
    }

}