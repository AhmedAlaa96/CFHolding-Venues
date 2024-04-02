package com.ahmed.cfholding_venues.domain.usecases.signup

import com.ahmed.cfholding_venues.data.models.Status
import com.ahmed.cfholding_venues.data.models.dto.User
import com.ahmed.cfholding_venues.ui.base.IBaseUseCase
import kotlinx.coroutines.flow.Flow

interface ISignupUseCase: IBaseUseCase {
    fun signUp(user: User): Flow<Status<Nothing>>
}