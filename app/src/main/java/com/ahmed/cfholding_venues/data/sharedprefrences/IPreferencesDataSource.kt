package com.ahmed.cfholding_venues.data.sharedprefrences

import com.ahmed.cfholding_venues.data.models.dto.User


interface IPreferencesDataSource {

    fun saveUserData(user: User)
    fun getUserData(): User?

    fun clearUserData()
}