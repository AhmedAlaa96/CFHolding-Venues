package com.ahmed.cfholding_venues.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import com.ahmed.cfholding_venues.databinding.ActivityMainBinding
import com.ahmed.cfholding_venues.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
    }

    override fun initializeViews() {
    }

    override fun setListeners() {
    }

    override val viewBindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

}