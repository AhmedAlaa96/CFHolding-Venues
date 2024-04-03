package com.ahmed.cfholding_venues.data.repositories.login

import com.ahmed.cfholding_venues.data.models.Status
import com.ahmed.cfholding_venues.data.models.dto.User
import com.ahmed.cfholding_venues.ui.base.IBaseRepository
import kotlinx.coroutines.flow.Flow

interface ILoginRepository: IBaseRepository {
    fun login(email: String, password: String): Flow<Status<User>>

    fun saveUserData(user: User)
}