package com.ahmed.cfholding_venues.ui.splash

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ahmed.cfholding_venues.data.models.ProgressTypes
import com.ahmed.cfholding_venues.data.models.Status
import com.ahmed.cfholding_venues.data.models.ValidationField
import com.ahmed.cfholding_venues.di.MainDispatcher
import com.ahmed.cfholding_venues.domain.usecases.login.ILoginUseCase
import com.ahmed.cfholding_venues.domain.usecases.splash.ISplashUseCase
import com.ahmed.cfholding_venues.ui.base.BaseViewModel
import com.ahmed.cfholding_venues.ui.base.ValidationViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val mUseCase: ISplashUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    handle: SavedStateHandle
) : BaseViewModel(handle, mUseCase) {
    private val _navigationMutableSharedFlow = MutableSharedFlow<Status<Boolean>>()
    val navigationMutableSharedFlow = _navigationMutableSharedFlow.asSharedFlow()


    fun isUserLoggedIn() {
        viewModelScope.launch {
            delay(100L)
            mUseCase.isUserLoggedIn().collect {
                _navigationMutableSharedFlow.emit(it)
            }

        }
    }


}