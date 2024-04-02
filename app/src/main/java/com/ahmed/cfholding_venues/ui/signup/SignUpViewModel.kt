package com.ahmed.cfholding_venues.ui.signup

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ahmed.cfholding_venues.data.models.ProgressTypes
import com.ahmed.cfholding_venues.data.models.Status
import com.ahmed.cfholding_venues.data.models.ValidationField
import com.ahmed.cfholding_venues.data.models.dto.User
import com.ahmed.cfholding_venues.di.MainDispatcher
import com.ahmed.cfholding_venues.domain.usecases.signup.ISignupUseCase
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
class SignUpViewModel @Inject constructor(
    private val mSignUpUseCase: ISignupUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    handle: SavedStateHandle
) : ValidationViewModel(handle, mSignUpUseCase) {
    private val _registerResponseMutableSharedFlow = MutableSharedFlow<Status<Nothing>>()
    val registerResponseSharedFlow = _registerResponseMutableSharedFlow.asSharedFlow()

    fun signUp(fName: String?, lName: String?, age: String?, email: String?, password: String?) {
        if (validateSignUpData(fName, lName, age, email, password)) {
            val user = User(
                fName = fName,
                lName = lName,
                age = age?.toInt(),
                email = email,
                password = password
            )
            val handler = CoroutineExceptionHandler { _, exception ->
                viewModelScope.launch {
                    setRegisterResponseStatus(Status.Error(error = exception.message))
                }
            }
            viewModelScope.launch(mainDispatcher + handler) {
                callSignUp(user)
            }
        } else {
            validateField(ValidationField.FirstName, fName)
            validateField(ValidationField.LastName, lName)
            validateField(ValidationField.Age, age)
            validateField(ValidationField.Email, email)
            validateField(ValidationField.RegisterPassword, password)
        }
    }

    private suspend fun callSignUp(
        user: User,
        progressType: ProgressTypes = ProgressTypes.FULL_PROGRESS
    ) {
        onRegisterSubscribe(progressType)
        mSignUpUseCase.signUp(user)
            .onStart {
                showProgress(true, progressType)
            }.onCompletion {
                showProgress(false)
            }.catch {
                setRegisterResponseStatus(Status.Error(error = it.message))
                showProgress(false, progressType)
            }
            .collect {
                setRegisterResponseStatus(it)
            }
    }

    private fun onRegisterSubscribe(progressType: ProgressTypes) {
        showProgress(true, progressType)
        shouldShowError(false)
    }

    private suspend fun setRegisterResponseStatus(loginResponseStatus: Status<Nothing>) {
        _registerResponseMutableSharedFlow.emit(loginResponseStatus)
    }

}