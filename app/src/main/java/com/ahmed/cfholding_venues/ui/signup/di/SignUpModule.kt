package com.ahmed.cfholding_venues.ui.signup.di

import com.ahmed.cfholding_venues.data.local.ILocalDataSource
import com.ahmed.cfholding_venues.data.remote.IRemoteDataSource
import com.ahmed.cfholding_venues.data.repositories.login.ILoginRepository
import com.ahmed.cfholding_venues.data.repositories.login.LoginRepository
import com.ahmed.cfholding_venues.data.repositories.signup.ISignUpRepository
import com.ahmed.cfholding_venues.data.repositories.signup.SignUpRepository
import com.ahmed.cfholding_venues.data.sharedprefrences.IPreferencesDataSource
import com.ahmed.cfholding_venues.domain.usecases.login.ILoginUseCase
import com.ahmed.cfholding_venues.domain.usecases.login.LoginUseCase
import com.ahmed.cfholding_venues.domain.usecases.signup.ISignupUseCase
import com.ahmed.cfholding_venues.domain.usecases.signup.SignupUseCase
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
abstract class SignUpModule {

    companion object {
        @Singleton
        @Provides
        fun provideSignUpRepository(
            connectionUtils: IConnectionUtils,
            mIRemoteDataSource: IRemoteDataSource,
            mILocalDataSource: ILocalDataSource,
            mIPreferencesDataSource: IPreferencesDataSource,
        ): ISignUpRepository {
            return SignUpRepository(
                connectionUtils,
                mIRemoteDataSource,
                mILocalDataSource,
                mIPreferencesDataSource,
            )
        }
    }

    @Singleton
    @Binds
    abstract fun bindISignUpUseCase(loginUseCase: SignupUseCase): ISignupUseCase
}