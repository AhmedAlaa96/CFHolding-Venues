package com.ahmed.cfholding_venues.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.ahmed.cfholding_venues.R
import com.ahmed.cfholding_venues.data.models.LoadingModel
import com.ahmed.cfholding_venues.data.models.ProgressTypes
import com.ahmed.cfholding_venues.data.models.StatusCode
import com.ahmed.cfholding_venues.data.models.dto.User
import com.ahmed.cfholding_venues.databinding.FragmentProfileBinding
import com.ahmed.cfholding_venues.ui.base.BaseFragment
import com.ahmed.cfholding_venues.ui.base.IToolbar
import com.ahmed.cfholding_venues.ui.home.HomeViewModel
import com.ahmed.cfholding_venues.utils.alternate
import com.ahmed.cfholding_venues.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(), IToolbar {

    private val viewModel: ProfileViewModel by viewModels()
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
        bindLoadingObserver()
        bindErrorObserver()
        bindToastMessageObserver()
        bindUserDataObserver()
    }

    private fun bindUserDataObserver() {
        observe(viewModel.userDataSharedFlow) { status ->
            when (status.statusCode) {
                StatusCode.SUCCESS -> {
                    onUserDataSuccess(status.data)
                }

                StatusCode.IDLE -> {}

                else -> {
                    onUserDataError(status.error)
                }
            }
        }
    }

    private fun onUserDataError(error: String?) {
        with(binding) {
            errorLayout.root.isVisible = true
            clContent.isVisible = false
            errorLayout.txtError.text =
                error?.alternate(getString(R.string.some_thing_went_wrong))
            errorLayout.btnRetry.isVisible = false
        }
    }

    private fun onUserDataSuccess(user: User?) {
        with(binding) {
            errorLayout.root.isVisible = false
            clContent.isVisible = true

            txtName.text =
                getString(R.string.profile_name, user?.fName?.alternate(), user?.lName?.alternate())
            txtAge.text =
                getString(R.string.profile_age, user?.age.toString().alternate())
            txtEmail.text =
                getString(R.string.profile_email, user?.email?.alternate())
        }


    }

    override fun showError(shouldShow: Boolean) {
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


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        navigateUp()
        return super.onOptionsItemSelected(item)
    }

    override var mToolbar: Toolbar?
        get() = binding.toolbarView.toolbar
        set(_) {}

}