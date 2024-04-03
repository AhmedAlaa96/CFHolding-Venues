package com.ahmed.cfholding_venues.domain.usecases.profile

import com.ahmed.cfholding_venues.data.models.Status
import com.ahmed.cfholding_venues.data.models.dto.User
import com.ahmed.cfholding_venues.ui.base.IBaseUseCase
import kotlinx.coroutines.flow.Flow

interface IProfileUseCase: IBaseUseCase {
    fun getUserData(): Flow<Status<User>>
}