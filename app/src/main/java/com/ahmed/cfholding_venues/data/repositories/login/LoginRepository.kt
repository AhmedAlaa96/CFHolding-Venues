package com.ahmed.cfholding_venues.data.repositories.login

import com.ahmed.cfholding_venues.data.local.ILocalDataSource
import com.ahmed.cfholding_venues.data.models.Status
import com.ahmed.cfholding_venues.data.models.dto.User
import com.ahmed.cfholding_venues.data.remote.IRemoteDataSource
import com.ahmed.cfholding_venues.data.sharedprefrences.IPreferencesDataSource
import com.ahmed.cfholding_venues.di.IoDispatcher
import com.ahmed.cfholding_venues.ui.base.BaseRepository
import com.ahmed.cfholding_venues.ui.base.IBaseRepository
import com.ahmed.cfholding_venues.utils.connection_utils.IConnectionUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginRepository @Inject constructor(
    connectionUtils: IConnectionUtils,
    mIRemoteDataSource: IRemoteDataSource,
    private val mILocalDataSource: ILocalDataSource,
    private val mIPreferencesDataSource: IPreferencesDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseRepository(connectionUtils, mIRemoteDataSource, mIPreferencesDataSource, dispatcher),
    ILoginRepository {
    override fun login(email: String, password: String): Flow<Status<User>> {
        return flow {
            emit(mILocalDataSource.getUserByEmailAndPassword(email, password))
        }.flowOn(dispatcher)
    }

    override fun saveUserData(user: User) {
        mIPreferencesDataSource.saveUserData(user)
    }
}