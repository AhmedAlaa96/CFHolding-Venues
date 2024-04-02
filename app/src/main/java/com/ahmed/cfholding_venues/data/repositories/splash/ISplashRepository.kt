package com.ahmed.cfholding_venues.data.repositories.splash

import com.ahmed.cfholding_venues.data.models.Status
import com.ahmed.cfholding_venues.data.models.dto.User
import com.ahmed.cfholding_venues.ui.base.IBaseRepository
import kotlinx.coroutines.flow.Flow

interface ISplashRepository: IBaseRepository {
  fun getUserData(): Flow<Status<User>>
}