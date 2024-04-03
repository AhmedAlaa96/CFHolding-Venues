package com.ahmed.cfholding_venues.ui.base

import androidx.annotation.CallSuper
import com.ahmed.cfholding_venues.data.models.Status
import com.ahmed.cfholding_venues.data.models.StatusCode
import com.google.gson.Gson


open class BaseUseCase(private val mBaseRepository: IBaseRepository, val mGson: Gson) :
    IBaseUseCase {

    @CallSuper
    protected fun validateResponse(statusResponse: Status<*>?): StatusCode {
        if (statusResponse == null)
            return StatusCode.ERROR

        if (statusResponse.isOfflineData())
            return StatusCode.OFFLINE_DATA

        if (statusResponse.isIdle())
            return StatusCode.IDLE

        if (statusResponse.isNoNetwork())
            return StatusCode.NO_NETWORK

        if (statusResponse.isError())
            return StatusCode.ERROR

        if (statusResponse.isServerError())
            return StatusCode.SERVER_ERROR

        if (statusResponse.data == null)
            return StatusCode.ERROR



        return StatusCode.VALID
    }

    inline fun <reified T> onServerError(
        status: Status<T>,
        onServerErrorResponse: (serverResponse: T) -> String?
    ): Status<T> {
        return if (status.error.isNullOrBlank())
            Status.Error(error = "")
        else {
            try {
                val response = mGson.fromJson(status.error, T::class.java)
                Status.ServerError(
                    error = onServerErrorResponse(response)
                )
            } catch (e: Exception) {
                Status.ServerError(
                    error = status.error
                )
            }
        }
    }

}
