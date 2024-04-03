package com.ahmed.cfholding_venues.ui.base

import com.ahmed.cfholding_venues.data.models.Status
import com.ahmed.cfholding_venues.data.remote.IRemoteDataSource
import com.ahmed.cfholding_venues.data.sharedprefrences.IPreferencesDataSource
import com.ahmed.cfholding_venues.utils.Utils
import com.ahmed.cfholding_venues.utils.connection_utils.IConnectionUtils
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.net.SocketException

abstract class BaseRepository(
    val connectionUtils: IConnectionUtils,
    private val mIRemoteDataSource: IRemoteDataSource,
    private val mIPreferencesDataSource: IPreferencesDataSource,
    val mGson: Gson,
    open val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : IBaseRepository {


    val isConnected: Boolean
        get() {
            return connectionUtils.isConnected
        }


    protected inline fun <reified T> safeApiCalls(
        crossinline apiCall: suspend () -> T
    ): Flow<Status<T>> {
        return flow {
            if (isConnected) {
                try {
                    emit(Status.Success(apiCall.invoke()))
                } catch (throwable: Throwable) {
                    Utils.printStackTrace(throwable)
                    when (throwable) {
                        is HttpException -> {
                            when (throwable.code()) {
                                in 400..499 -> {
                                    emit(Status.Error(error = throwable.message)) as Unit
                                }

                                else -> {
                                    emit(Status.Error(error = throwable.message)) as Unit
                                }
                            }
                        }

                        is SocketException -> {
                            emit(Status.Error(error = throwable.message)) as Unit
                        }

                        else -> {
                            emit(Status.Error(error = throwable.message)) as Unit
                        }
                    }
                }
            } else {
                emit(Status.NoNetwork(error = "No Network")) as Unit
            }
        }.flowOn((dispatcher)) as Flow<Status<T>>
    }
}
