package com.ahmed.cfholding_venues.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ahmed.cfholding_venues.databinding.FragmentLoginBinding
import com.ahmed.cfholding_venues.ui.base.BaseFragment

class LoginFragment: BaseFragment<FragmentLoginBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate

    override fun onActivityReady(savedInstanceState: Bundle?) {
    }

    override fun initViews() {
    }

    override fun setListeners() {
    }

    override fun bindViewModels() {
    }

    override fun showError(shouldShow: Boolean) {
    }
}