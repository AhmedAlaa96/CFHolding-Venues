package com.ahmed.cfholding_venues.ui.home.di

import com.ahmed.cfholding_venues.data.local.ILocalDataSource
import com.ahmed.cfholding_venues.data.remote.IRemoteDataSource
import com.ahmed.cfholding_venues.data.repositories.home.HomeRepository
import com.ahmed.cfholding_venues.data.repositories.home.IHomeRepository
import com.ahmed.cfholding_venues.data.repositories.login.ILoginRepository
import com.ahmed.cfholding_venues.data.repositories.login.LoginRepository
import com.ahmed.cfholding_venues.data.sharedprefrences.IPreferencesDataSource
import com.ahmed.cfholding_venues.domain.usecases.home.HomeUseCase
import com.ahmed.cfholding_venues.domain.usecases.home.IHomeUseCase
import com.ahmed.cfholding_venues.domain.usecases.login.ILoginUseCase
import com.ahmed.cfholding_venues.domain.usecases.login.LoginUseCase
import com.ahmed.cfholding_venues.utils.connection_utils.IConnectionUtils
import com.ahmed.cfholding_venues.retrofit.RetrofitModule
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [RetrofitModule::class])
@InstallIn(SingletonComponent::class)
abstract class HomeModule {

    companion object {
        @Singleton
        @Provides
        fun provideHomeRepository(
            connectionUtils: IConnectionUtils,
            mIRemoteDataSource: IRemoteDataSource,
            mILocalDataSource: ILocalDataSource,
            mIPreferencesDataSource: IPreferencesDataSource
        ): IHomeRepository {
            return HomeRepository(
                connectionUtils,
                mIRemoteDataSource,
                mILocalDataSource,
                mIPreferencesDataSource
            )
        }
    }

    @Singleton
    @Binds
    abstract fun bindIHomeUseCase(homeUseCase: HomeUseCase): IHomeUseCase
}