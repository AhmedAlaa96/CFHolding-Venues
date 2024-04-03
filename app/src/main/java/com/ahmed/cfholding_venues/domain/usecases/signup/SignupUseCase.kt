package com.ahmed.cfholding_venues.domain.usecases.signup

import com.ahmed.cfholding_venues.data.models.Status
import com.ahmed.cfholding_venues.data.models.dto.User
import com.ahmed.cfholding_venues.data.repositories.signup.ISignUpRepository
import com.ahmed.cfholding_venues.ui.base.BaseUseCase
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SignupUseCase @Inject constructor(private val repository: ISignUpRepository, mGson: Gson) :
    BaseUseCase(repository, mGson), ISignupUseCase {
    override fun signUp(user: User): Flow<Status<Nothing>> {
        return repository.signUp(user)
            .map { status ->
                return@map if (status.isSuccess()) {
                    status.data?.let {
                        repository.saveUserData(it)
                    }
                    Status.Success(null)
                } else {
                    Status.CopyStatus(status, null)
                }

            }
    }
}