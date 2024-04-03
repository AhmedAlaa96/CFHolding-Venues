package com.ahmed.cfholding_venues.ui.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.ahmed.cfholding_venues.R
import com.ahmed.cfholding_venues.data.models.LoadingModel
import com.ahmed.cfholding_venues.data.models.ProgressTypes
import com.ahmed.cfholding_venues.data.models.ValidationField
import com.ahmed.cfholding_venues.databinding.FragmentSignupBinding
import com.ahmed.cfholding_venues.ui.base.BaseFragment
import com.ahmed.cfholding_venues.ui.login.LoginFragmentDirections
import com.ahmed.cfholding_venues.utils.Constants
import com.ahmed.cfholding_venues.utils.alternate
import com.ahmed.cfholding_venues.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : BaseFragment<FragmentSignupBinding>() {

    private val viewModel: SignUpViewModel by viewModels()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSignupBinding
        get() = FragmentSignupBinding::inflate

    override fun onActivityReady(savedInstanceState: Bundle?) {
    }

    override fun initViews() {
        with(binding) {
            editTextFName.addTextChangedListener(textWatcher)
            editTextLName.addTextChangedListener(textWatcher)
            editTextAge.addTextChangedListener(textWatcher)
            editTextEmail.addTextChangedListener(textWatcher)
            editTextPassword.addTextChangedListener(textWatcher)
        }
    }

    override fun setListeners() {
        with(binding) {
            btnSignUp.setOnClickListener {
                resetErrorLayouts()
                viewModel.signUp(
                    editTextFName.text.toString().trim(),
                    editTextLName.text.toString().trim(),
                    editTextAge.text.toString().trim(),
                    editTextEmail.text.toString().trim(),
                    editTextPassword.text.toString().trim()
                )
            }

            btnLogin.setOnClickListener {
                navigateTo(SignupFragmentDirections.actionToLoginFragment())
            }
        }
    }

    override fun bindViewModels() {
        bindLoadingObserver()
        bindErrorObserver()
        bindToastMessageObserver()
        bindValidationObserver()
        bindLoginResponseObservable()
    }

    private fun bindLoginResponseObservable() {
        observe(viewModel.registerResponseSharedFlow) { status ->
            if (status.isSuccess()) {
                navigateTo(SignupFragmentDirections.actionToHomeFragment())
            } else {
                if (status.error == Constants.General.EMAIL_EXISTS) {
                    binding.textInputLayoutEmail.error = status.error.alternate("")

                } else {
                    binding.textInputLayoutPassword.error = status.error.alternate("")

                }
            }
        }
    }

    override fun showError(shouldShow: Boolean) {
    }


    private fun bindValidationObserver() {
        observe(viewModel.validationObservable) { (type, isValid) ->
            onValidationRetrieved(isValid, type)
        }
    }

    private fun onValidationRetrieved(
        isValid: Boolean,
        type: ValidationField
    ) {
        with(binding) {
            if (!isValid) {
                when (type) {
                    ValidationField.FirstName -> {
                        textInputLayoutFName.error = getString(R.string.name_validation)
                    }

                    ValidationField.LastName -> {
                        textInputLayoutLName.error = getString(R.string.name_validation)
                    }

                    ValidationField.Age -> {
                        textInputLayoutAge.error = getString(R.string.age_validation)
                    }

                    ValidationField.Email -> {
                        textInputLayoutEmail.error = getString(R.string.email_validation)
                    }

                    ValidationField.RegisterPassword -> {
                        textInputLayoutPassword.error =
                            getString(R.string.register_password_validation)
                    }

                    else -> {}
                }
            }
        }
    }

    private fun bindLoadingObserver() {
        observe(viewModel.loadingObservable) {
            onLoadingObserverRetrieved(it)
        }
    }


    private fun onLoadingObserverRetrieved(loadingModel: LoadingModel) {
        loadingModel.loadingProgressView = binding.viewProgress.loadingIndicator
        loadingModel.loadingFullProgressView = binding.viewFullProgress.root
        binding.viewProgress.root.isVisible =
            (loadingModel.shouldShow && loadingModel.progressType == ProgressTypes.MAIN_PROGRESS)

        binding.viewFullProgress.root.isVisible =
            (loadingModel.shouldShow && loadingModel.progressType == ProgressTypes.FULL_PROGRESS)
    }

    private fun bindErrorObserver() {
        observe(viewModel.errorViewObservable) {
            showError(it)
            binding.errorLayout.root.visibility = View.GONE
        }
    }

    private fun bindToastMessageObserver() {
        observe(viewModel.showToastObservable) {
            showMessage(it)
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            resetErrorLayouts()
        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }

    private fun resetErrorLayouts() {
        binding.textInputLayoutFName.error = null
        binding.textInputLayoutLName.error = null
        binding.textInputLayoutAge.error = null
        binding.textInputLayoutEmail.error = null
        binding.textInputLayoutPassword.error = null
    }

}