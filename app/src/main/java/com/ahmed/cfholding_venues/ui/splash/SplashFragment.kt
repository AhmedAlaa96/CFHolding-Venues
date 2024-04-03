package com.ahmed.cfholding_venues.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ahmed.cfholding_venues.R
import com.ahmed.cfholding_venues.data.models.StatusCode
import com.ahmed.cfholding_venues.databinding.FragmentSplashBinding
import com.ahmed.cfholding_venues.ui.base.BaseFragment
import com.ahmed.cfholding_venues.ui.login.LoginViewModel
import com.ahmed.cfholding_venues.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    private val viewModel: SplashViewModel by viewModels()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSplashBinding
        get() = FragmentSplashBinding::inflate

    override fun onActivityReady(savedInstanceState: Bundle?) {
    }

    override fun initViews() {
    }

    override fun setListeners() {
    }

    override fun bindViewModels() {
        bindIsUserLoggedIn()
        viewModel.isUserLoggedIn()
    }

    private fun bindIsUserLoggedIn() {
        observe(viewModel.navigationMutableSharedFlow) { status ->
            when (status.statusCode) {
                StatusCode.SUCCESS -> {
                    if (status.data == true) {
                        navigateTo(SplashFragmentDirections.actionToHomeFragment())
                    } else {
                        navigateTo(SplashFragmentDirections.actionToLoginFragment())
                    }
                }

                else -> navigateTo(SplashFragmentDirections.actionToLoginFragment())
            }

        }
    }

    override fun showError(shouldShow: Boolean) {
    }

}