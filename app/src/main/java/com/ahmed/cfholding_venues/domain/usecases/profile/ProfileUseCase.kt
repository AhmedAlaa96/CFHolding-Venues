package com.ahmed.cfholding_venues.domain.usecases.profile

import com.ahmed.cfholding_venues.data.models.Status
import com.ahmed.cfholding_venues.data.models.dto.User
import com.ahmed.cfholding_venues.data.repositories.profile.IProfileRepository
import com.ahmed.cfholding_venues.data.repositories.signup.ISignUpRepository
import com.ahmed.cfholding_venues.data.repositories.splash.ISplashRepository
import com.ahmed.cfholding_venues.ui.base.BaseUseCase
import com.ahmed.cfholding_venues.ui.base.IBaseUseCase
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProfileUseCase @Inject constructor(private val repository: IProfileRepository, mGson: Gson) :
    BaseUseCase(repository, mGson), IProfileUseCase {
    override fun getUserData(): Flow<Status<User>> {
        return repository.getUserData()
    }
}