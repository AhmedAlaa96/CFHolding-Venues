package com.ahmed.cfholding_venues.ui.terms

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.ahmed.cfholding_venues.R
import com.ahmed.cfholding_venues.databinding.FragmentTermsBinding
import com.ahmed.cfholding_venues.ui.base.BaseFragment
import com.ahmed.cfholding_venues.ui.base.IToolbar
import com.ahmed.cfholding_venues.utils.Constants

class TermsFragment : BaseFragment<FragmentTermsBinding>(), IToolbar {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentTermsBinding
        get() = FragmentTermsBinding::inflate

    override var mToolbar: Toolbar?
        get() = binding.toolbarView.toolbar
        set(_) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityReady(savedInstanceState: Bundle?) {
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        navigateUp()
        return super.onOptionsItemSelected(item)
    }

    override fun initViews() {
        setToolbar(mContext, getString(R.string.terms), true)
        binding.txtTerms.text =
            Html.fromHtml(Constants.DummyData.HTML_TEXT, Html.FROM_HTML_MODE_COMPACT)
    }

    override fun setListeners() {
    }

    override fun bindViewModels() {
    }

    override fun showError(shouldShow: Boolean) {
    }

}