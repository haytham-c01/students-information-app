package com.haytham.coder.graduationproject.presentation.activity

import android.content.res.ColorStateList
import android.os.Bundle
import android.transition.TransitionManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.tabs.TabLayout
import com.haytham.coder.graduationproject.R
import com.haytham.coder.graduationproject.databinding.ActivityMainBinding
import com.haytham.coder.graduationproject.databinding.MenuItemBottomTabsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener {
    private lateinit var dataBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        dataBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        val actionView = dataBinding
            .bottomBar
            .menu
            .getItem(0)
            .actionView

        val tabBarBinding = actionView?.let { MenuItemBottomTabsBinding.bind(it) }
        tabBarBinding?.tabs?.apply {
            addOnTabSelectedListener(this@MainActivity)

            (dataBinding.navHostFragment as? NavHostFragment)?.navController?.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.homeFragment -> tabBarBinding.tabs.getTabAt(0)?.select()
                    R.id.searchFragment -> tabBarBinding.tabs.getTabAt(1)?.select()
                    R.id.filterFragment -> tabBarBinding.tabs.getTabAt(2)?.select()
                }
            }
        }
    }

    fun enableFab() {
        dataBinding.fab.apply {
            isEnabled = true
            imageTintList = ColorStateList.valueOf(resources.getColor(android.R.color.white))
        }
    }

    fun disableFab() {
        dataBinding.fab.apply {
            isEnabled = false
            imageTintList = ColorStateList.valueOf(resources.getColor(android.R.color.darker_gray))
        }
    }

    val isFabEnabled get() = dataBinding.fab.isEnabled

    fun showBottomBar() {
        dataBinding.apply {
            bottomBar.isVisible = true
            fab.show()

        }
    }

    fun hideBottomBar() {
        dataBinding.apply {
            fab.hide()
            bottomBar.isVisible = false
        }
    }

    fun setupStudentAddFab() {
        dataBinding.apply {
            fab.setOnClickListener {
                findNavController(R.id.nav_host_fragment).navigate(R.id.addStudentFragment)
            }
        }
    }

    override fun onTabReselected(tab: TabLayout.Tab) {

    }

    override fun onTabUnselected(tab: TabLayout.Tab) {
        tab.text = ""
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        val navController = findNavController(R.id.nav_host_fragment)

        TransitionManager.beginDelayedTransition(tab.parent)
        when (tab.position) {
            0 -> {
                tab.setText(R.string.tab_home_label)
                navController.navigate(R.id.action_global_homeFragment)
            }

            1 -> {
                tab.setText(R.string.tab_search_label)
                navController.navigate(R.id.action_global_searchFragment)
            }

            else -> {
                tab.setText(R.string.tab_filter_label)
                navController.navigate(R.id.action_global_filterFragment)
            }
        }


    }
}

