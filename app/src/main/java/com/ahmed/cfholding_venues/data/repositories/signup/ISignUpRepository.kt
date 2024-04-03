package com.ahmed.cfholding_venues.data.repositories.signup

import com.ahmed.cfholding_venues.data.models.Status
import com.ahmed.cfholding_venues.data.models.dto.User
import com.ahmed.cfholding_venues.ui.base.IBaseRepository
import kotlinx.coroutines.flow.Flow

interface ISignUpRepository: IBaseRepository {
    fun signUp(user: User): Flow<Status<User>>

    fun saveUserData(user: User)
}