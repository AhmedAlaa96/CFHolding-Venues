package com.ahmed.cfholding_venues.ui.base

import android.util.Patterns
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ahmed.cfholding_venues.data.models.ValidationField
import com.ahmed.cfholding_venues.utils.Constants
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

abstract class ValidationViewModel(
    handle: SavedStateHandle,
    vararg mUseCases: IBaseUseCase
) : BaseViewModel(handle, *mUseCases) {
    var validationObservable = MutableSharedFlow<Pair<ValidationField, Boolean>>()


    fun validateField(type: ValidationField, value: String?) {
        val isValid = when (type) {
            ValidationField.Age -> validateAge(value)
            ValidationField.Email -> validateEmail(value)
            ValidationField.FirstName -> validateName(value)
            ValidationField.LastName -> validateName(value)
            ValidationField.RegisterPassword -> validateRegisterPassword(value)
            ValidationField.LoginPassword -> validateLoginPassword(value)
        }

        viewModelScope.launch {
            validationObservable.emit(Pair(type, isValid))
        }
    }

    fun validateLoginData(email: String?, password: String?): Boolean {
        return validateEmail(email) && validateLoginPassword(password)
    }

    fun validateSignUpData(
        fName: String?,
        lName: String?,
        age: String?,
        email: String?,
        password: String?
    ): Boolean {
        return validateName(fName)
                && validateName(lName)
                && validateAge(age)
                && validateEmail(email)
                && validateRegisterPassword(password)
    }

    private fun validateName(name: String?): Boolean {
        return !name.isNullOrEmpty()
    }

    private fun validateRegisterPassword(password: String?): Boolean {
        return !password.isNullOrEmpty() && password.length >= 8 && password.contains(Constants.General.PASSWORD_REGEX.toRegex())
    }

    private fun validateLoginPassword(password: String?): Boolean {
        return !password.isNullOrEmpty()
    }

    private fun validateEmail(email: String?): Boolean {
        return !email.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun validateAge(age: String?): Boolean {
        return age?.toIntOrNull()?.let {
            it >= 18
        } ?: false
    }

}
