package com.ahmed.cfholding_venues.ui.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ahmed.cfholding_venues.data.models.ProgressTypes
import com.ahmed.cfholding_venues.data.models.Status
import com.ahmed.cfholding_venues.data.models.ValidationField
import com.ahmed.cfholding_venues.di.MainDispatcher
import com.ahmed.cfholding_venues.domain.usecases.login.ILoginUseCase
import com.ahmed.cfholding_venues.ui.base.BaseViewModel
import com.ahmed.cfholding_venues.ui.base.ValidationViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val mLoginUseCase: ILoginUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    handle: SavedStateHandle
) : ValidationViewModel(handle, mLoginUseCase) {
    private val _loginResponseMutableSharedFlow = MutableSharedFlow<Status<Nothing>>()
    val loginResponseSharedFlow = _loginResponseMutableSharedFlow.asSharedFlow()

    fun login(email: String?, password: String?) {
        if (validateLoginData(email, password)) {
            val handler = CoroutineExceptionHandler { _, exception ->
                viewModelScope.launch {
                    setLoginResponseStatus(Status.Error(error = exception.message))
                }
            }
            viewModelScope.launch(mainDispatcher + handler) {
                callLogin(email!!, password!!)
            }
        } else {
            validateField(ValidationField.Email, email)
            validateField(ValidationField.LoginPassword, password)
        }
    }

    private suspend fun callLogin(
        email: String,
        password: String,
        progressType: ProgressTypes = ProgressTypes.FULL_PROGRESS
    ) {
        onLoginSubscribe(progressType)
        mLoginUseCase.login(email, password)
            .onStart {
                showProgress(true, progressType)
            }.onCompletion {
                showProgress(false)
            }.catch {
                setLoginResponseStatus(Status.Error(error = it.message))
                showProgress(false, progressType)
            }
            .collect {
                setLoginResponseStatus(it)
            }
    }

    private fun onLoginSubscribe(progressType: ProgressTypes) {
        showProgress(true, progressType)
        shouldShowError(false)
    }

    private suspend fun setLoginResponseStatus(loginResponseStatus: Status<Nothing>) {
        _loginResponseMutableSharedFlow.emit(loginResponseStatus)
    }

}