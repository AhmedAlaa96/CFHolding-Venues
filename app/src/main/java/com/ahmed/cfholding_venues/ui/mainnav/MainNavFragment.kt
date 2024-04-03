package com.ahmed.cfholding_venues.ui.mainnav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.ahmed.cfholding_venues.R
import com.ahmed.cfholding_venues.databinding.FragmentMainNavBinding
import com.ahmed.cfholding_venues.ui.base.BaseFragment
import com.ahmed.cfholding_venues.ui.base.IToolbar
import androidx.navigation.ui.setupWithNavController
import com.ahmed.cfholding_venues.ui.home.HomeViewModel
import com.ahmed.cfholding_venues.utils.utilities.UIUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainNavFragment : BaseFragment<FragmentMainNavBinding>(), IToolbar {

    private lateinit var navController: NavController
    private val viewModel: HomeViewModel by viewModels()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainNavBinding
        get() = FragmentMainNavBinding::inflate

    override var mToolbar: Toolbar?
        get() = binding.toolbarView.toolbar
        set(_) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityReady(savedInstanceState: Bundle?) {
    }

    override fun initViews() {
        setToolbar(mContext, getString(R.string.home), true, R.drawable.ic_menu)
        initNavController()
        initDrawer()

    }

    private fun initNavController() {
        navController = binding.homeNavHostFragment.findNavController()
        binding.navView.setupWithNavController(navController)
    }

    override fun setListeners() {
        with(binding) {
            navView.setNavigationItemSelectedListener { item ->
                onItemSelected(item)
            }
        }
    }

    private fun onItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_profile -> {
                navigateTo(MainNavFragmentDirections.actionToProfileNav())
            }

            R.id.nav_terms -> {
                navigateTo(MainNavFragmentDirections.actionToTermsNav())
            }

            R.id.nav_logout -> {
                UIUtils.showBasicDialog(
                    mContext,
                    getString(R.string.logout),
                    getString(R.string.logout_desc),
                    getString(R.string.logout),
                    getString(R.string.cancel),
                    { _, _ ->
                        viewModel.logout()
                        navigateToAndChangeStartDestination(
                            R.navigation.app_nav,
                            R.id.loginFragment,
                            navOptions = NavOptions.Builder()
                                .setLaunchSingleTop(true)
                                .setPopUpTo(R.id.mainNavFragment, true)
                                .build()
                        )
                    },
                    { dialog, _ ->
                        dialog.dismiss()
                    }

                )
            }
        }

        binding.navView.menu.getItem(0).isChecked = true
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onResume() {
        super.onResume()
        binding.navView.menu.getItem(0).isChecked = true
    }


    override fun bindViewModels() {
    }


    private fun initDrawer() {
        val toggle = ActionBarDrawerToggle(
            requireActivity(),
            binding.drawerLayout,
            mToolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.navView.menu.getItem(0).isChecked = true
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun showError(shouldShow: Boolean) {
        TODO("Not yet implemented")
    }

}