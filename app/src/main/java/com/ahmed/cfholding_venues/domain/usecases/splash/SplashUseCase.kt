package com.ahmed.cfholding_venues.domain.usecases.splash

import com.ahmed.cfholding_venues.data.models.Status
import com.ahmed.cfholding_venues.data.models.dto.User
import com.ahmed.cfholding_venues.data.repositories.signup.ISignUpRepository
import com.ahmed.cfholding_venues.data.repositories.splash.ISplashRepository
import com.ahmed.cfholding_venues.ui.base.BaseUseCase
import com.ahmed.cfholding_venues.ui.base.IBaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SplashUseCase @Inject constructor(private val repository: ISplashRepository) :
    BaseUseCase(repository), ISplashUseCase {
    override fun isUserLoggedIn(): Flow<Status<Boolean>> {
        return repository.getUserData().map { status ->
            Status.Success(status.data != null)
        }
    }
}