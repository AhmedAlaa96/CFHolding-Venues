package com.ahmed.cfholding_venues.domain.usecases.splash

import com.ahmed.cfholding_venues.data.models.Status
import com.ahmed.cfholding_venues.data.models.dto.User
import com.ahmed.cfholding_venues.ui.base.IBaseUseCase
import kotlinx.coroutines.flow.Flow

interface ISplashUseCase: IBaseUseCase {
    fun isUserLoggedIn(): Flow<Status<Boolean>>
}