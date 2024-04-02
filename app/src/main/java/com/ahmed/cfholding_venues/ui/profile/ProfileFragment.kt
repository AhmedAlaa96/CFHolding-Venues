package com.ahmed.cfholding_venues.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.ahmed.cfholding_venues.R
import com.ahmed.cfholding_venues.databinding.FragmentProfileBinding
import com.ahmed.cfholding_venues.ui.base.BaseFragment
import com.ahmed.cfholding_venues.ui.base.IToolbar

class ProfileFragment : BaseFragment<FragmentProfileBinding>(), IToolbar {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileBinding
        get() = FragmentProfileBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityReady(savedInstanceState: Bundle?) {
    }

    override fun initViews() {
        setToolbar(mContext, getString(R.string.profile), true)
    }

    override fun setListeners() {
    }

    override fun bindViewModels() {
    }

    override fun showError(shouldShow: Boolean) {
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        navigateUp()
        return super.onOptionsItemSelected(item)
    }

    override var mToolbar: Toolbar?
        get() = binding.toolbarView.toolbar
        set(_) {}

}