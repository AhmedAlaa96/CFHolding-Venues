package com.ahmed.cfholding_venues.data.repositories.signup

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

class SignUpRepository @Inject constructor(
    connectionUtils: IConnectionUtils,
    mIRemoteDataSource: IRemoteDataSource,
    private val mILocalDataSource: ILocalDataSource,
    private val mIPreferencesDataSource: IPreferencesDataSource,
    mGson: Gson,
    @IoDispatcher override val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseRepository(connectionUtils, mIRemoteDataSource, mIPreferencesDataSource, mGson, dispatcher),
    ISignUpRepository {
    override fun signUp(user: User): Flow<Status<User>> {
        return flow<Status<User>> {
            emit(mILocalDataSource.signUp(user))
        }.flowOn(dispatcher)
    }

    override fun saveUserData(user: User) {
        mIPreferencesDataSource.saveUserData(user)
    }
}