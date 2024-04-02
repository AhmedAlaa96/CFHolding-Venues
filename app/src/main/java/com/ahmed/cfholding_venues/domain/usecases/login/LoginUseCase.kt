package com.ahmed.cfholding_venues.domain.usecases.login

import com.ahmed.cfholding_venues.data.models.Status
import com.ahmed.cfholding_venues.data.repositories.login.ILoginRepository
import com.ahmed.cfholding_venues.ui.base.BaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: ILoginRepository) :
    BaseUseCase(loginRepository), ILoginUseCase {
    override fun login(email: String, password: String): Flow<Status<Nothing>> {
        return loginRepository.login(email, password).map { status ->
            return@map if (status.isSuccess()) {
                status.data?.let {
                    loginRepository.saveUserData(it)
                }
                Status.Success(null)
            } else {
                Status.CopyStatus(status, null)
            }
        }
    }
}