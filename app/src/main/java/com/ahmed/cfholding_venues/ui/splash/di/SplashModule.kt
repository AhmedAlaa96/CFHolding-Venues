package com.ahmed.cfholding_venues.ui.splash.di

import com.ahmed.cfholding_venues.data.local.ILocalDataSource
import com.ahmed.cfholding_venues.data.remote.IRemoteDataSource
import com.ahmed.cfholding_venues.data.repositories.login.ILoginRepository
import com.ahmed.cfholding_venues.data.repositories.login.LoginRepository
import com.ahmed.cfholding_venues.data.repositories.splash.ISplashRepository
import com.ahmed.cfholding_venues.data.repositories.splash.SplashRepository
import com.ahmed.cfholding_venues.data.sharedprefrences.IPreferencesDataSource
import com.ahmed.cfholding_venues.domain.usecases.login.ILoginUseCase
import com.ahmed.cfholding_venues.domain.usecases.login.LoginUseCase
import com.ahmed.cfholding_venues.domain.usecases.splash.ISplashUseCase
import com.ahmed.cfholding_venues.domain.usecases.splash.SplashUseCase
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
abstract class SplashModule {

    companion object {
        @Singleton
        @Provides
        fun provideSplashRepository(
            connectionUtils: IConnectionUtils,
            mIRemoteDataSource: IRemoteDataSource,
            mILocalDataSource: ILocalDataSource,
            mIPreferencesDataSource: IPreferencesDataSource,
            mGson: Gson,
        ): ISplashRepository {
            return SplashRepository(
                connectionUtils,
                mIRemoteDataSource,
                mILocalDataSource,
                mIPreferencesDataSource,
                mGson
            )
        }
    }

    @Singleton
    @Binds
    abstract fun bindISplashUseCase(splashUseCase: SplashUseCase): ISplashUseCase
}