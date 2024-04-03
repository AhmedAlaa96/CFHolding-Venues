package com.ahmed.cfholding_venues.data.local

import com.ahmed.cfholding_venues.data.models.Status
import com.ahmed.cfholding_venues.data.models.dto.User

interface ILocalDataSource {
    suspend fun getUserByEmailAndPassword(email: String, password: String): Status<User>
    suspend fun signUp(user:User): Status<User>
}