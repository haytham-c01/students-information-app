package com.haytham.coder.graduationproject.presentation

import android.os.Bundle
import android.transition.TransitionManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
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
        dataBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )

        val actionView = dataBinding
            .bottomBar
            .menu
            .getItem(0)
            .actionView

        val tabBarBinding = MenuItemBottomTabsBinding.bind(actionView)
        tabBarBinding.tabs.apply {
            addOnTabSelectedListener(this@MainActivity)

            findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener { _, destination, _ ->
                when(destination.id){
                    R.id.homeFragment -> tabBarBinding.tabs.getTabAt(0)?.select()
                    R.id.searchFragment -> tabBarBinding.tabs.getTabAt(1)?.select()
                    R.id.filterFragment -> tabBarBinding.tabs.getTabAt(2)?.select()
                }
            }
        }



    }

    fun showBottomBar() {
        dataBinding.apply {
                bottomBar.isVisible = true
                fab.isVisible = true
        }
    }

    override fun onTabReselected(tab: TabLayout.Tab) {

    }

    override fun onTabUnselected(tab: TabLayout.Tab) {
        tab.text= ""
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        val navController= findNavController(R.id.nav_host_fragment)

        TransitionManager.beginDelayedTransition(tab.parent)
        when (tab.position) {
            0-> {
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

