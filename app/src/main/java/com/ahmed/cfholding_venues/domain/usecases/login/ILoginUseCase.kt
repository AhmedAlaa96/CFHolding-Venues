package com.ahmed.cfholding_venues.domain.usecases.login

import com.ahmed.cfholding_venues.data.models.Status
import com.ahmed.cfholding_venues.ui.base.IBaseUseCase
import kotlinx.coroutines.flow.Flow

interface ILoginUseCase: IBaseUseCase {
    fun login(email: String, password: String): Flow<Status<Nothing>>
}