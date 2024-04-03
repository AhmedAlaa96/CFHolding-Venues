package com.ahmed.cfholding_venues.ui.profile.di

import com.ahmed.cfholding_venues.data.local.ILocalDataSource
import com.ahmed.cfholding_venues.data.remote.IRemoteDataSource
import com.ahmed.cfholding_venues.data.repositories.home.HomeRepository
import com.ahmed.cfholding_venues.data.repositories.home.IHomeRepository
import com.ahmed.cfholding_venues.data.repositories.login.ILoginRepository
import com.ahmed.cfholding_venues.data.repositories.login.LoginRepository
import com.ahmed.cfholding_venues.data.repositories.profile.IProfileRepository
import com.ahmed.cfholding_venues.data.repositories.profile.ProfileRepository
import com.ahmed.cfholding_venues.data.sharedprefrences.IPreferencesDataSource
import com.ahmed.cfholding_venues.domain.usecases.home.HomeUseCase
import com.ahmed.cfholding_venues.domain.usecases.home.IHomeUseCase
import com.ahmed.cfholding_venues.domain.usecases.login.ILoginUseCase
import com.ahmed.cfholding_venues.domain.usecases.login.LoginUseCase
import com.ahmed.cfholding_venues.domain.usecases.profile.IProfileUseCase
import com.ahmed.cfholding_venues.domain.usecases.profile.ProfileUseCase
import com.ahmed.cfholding_venues.utils.connection_utils.IConnectionUtils
import com.ahmed.cfholding_venues.retrofit.RetrofitModule
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [RetrofitModule::class])
@InstallIn(SingletonComponent::class)
abstract class ProfileModule {

    companion object {
        @Singleton
        @Provides
        fun provideProfileRepository(
            connectionUtils: IConnectionUtils,
            mIRemoteDataSource: IRemoteDataSource,
            mILocalDataSource: ILocalDataSource,
            mIPreferencesDataSource: IPreferencesDataSource,
        ): IProfileRepository {
            return ProfileRepository(
                connectionUtils,
                mIRemoteDataSource,
                mILocalDataSource,
                mIPreferencesDataSource,
            )
        }
    }

    @Singleton
    @Binds
    abstract fun bindIProfileUseCase(profileUseCase: ProfileUseCase): IProfileUseCase
}