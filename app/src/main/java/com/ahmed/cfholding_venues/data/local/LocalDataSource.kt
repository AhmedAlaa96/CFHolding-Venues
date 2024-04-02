package com.ahmed.cfholding_venues.data.local

import com.ahmed.cfholding_venues.data.models.Status
import com.ahmed.cfholding_venues.data.models.dto.User
import com.ahmed.cfholding_venues.data.room.AppDatabase
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val appDatabase: AppDatabase) : ILocalDataSource {
    override suspend fun getUserByEmailAndPassword(email: String, password: String): Status<User> {
        val user = appDatabase.usersDao().getUserByEmailAndPassword(email, password)
        return if (user != null) {
            Status.Success(user)
        } else {
            Status.NoData(error = "incorrect email or Password")
        }
    }

    override suspend fun signUp(user: User): Status<User> {
        appDatabase.usersDao().insertUser(user)

        return Status.Success(user)
    }
}