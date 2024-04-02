package com.ahmed.cfholding_venues.domain.usecases.signup

import com.ahmed.cfholding_venues.data.models.Status
import com.ahmed.cfholding_venues.data.models.dto.User
import com.ahmed.cfholding_venues.data.repositories.login.ILoginRepository
import com.ahmed.cfholding_venues.data.repositories.signup.ISignUpRepository
import com.ahmed.cfholding_venues.data.repositories.signup.SignUpRepository
import com.ahmed.cfholding_venues.ui.base.BaseUseCase
import com.ahmed.cfholding_venues.ui.base.IBaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SignupUseCase @Inject constructor(private val signUpRepository: ISignUpRepository) :
    BaseUseCase(signUpRepository), ISignupUseCase {
    override fun signUp(user: User): Flow<Status<Nothing>> {
        return signUpRepository.signUp(user)
            .map { status ->
                return@map if (status.isSuccess()) {
                    status.data?.let {
                        signUpRepository.saveUserData(it)
                    }
                    Status.Success(null)
                } else {
                    Status.CopyStatus(status, null)
                }

            }
    }
}