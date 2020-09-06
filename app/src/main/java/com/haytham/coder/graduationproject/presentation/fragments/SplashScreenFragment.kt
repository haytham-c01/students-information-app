package com.haytham.coder.graduationproject.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.haytham.coder.graduationproject.R
import com.haytham.coder.graduationproject.databinding.FragmentSplashScreenBinding
import com.haytham.coder.graduationproject.domain.usecase.contract.IGetUserUseCase
import com.haytham.coder.graduationproject.presentation.activity.MainActivity
import com.haytham.coder.graduationproject.presentation.adapters.setLayoutMarginTop
import com.haytham.coder.graduationproject.presentation.fragments.authentication.SignInFragmentDirections
import com.haytham.coder.graduationproject.utils.AuthError
import com.haytham.coder.graduationproject.utils.Authenticated
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * this fragment is not used
 */
@AndroidEntryPoint
class SplashScreenFragment() : Fragment() {
    lateinit var binding: FragmentSplashScreenBinding
    @Inject
    lateinit var getUserUseCase: IGetUserUseCase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashScreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.logo.setLayoutMarginTop(getBottomNavHeight())

//        val extras = FragmentNavigatorExtras(
//            binding.logo to binding.logo.transitionName,
//        )
        viewLifecycleOwner.lifecycleScope.launch {
            when(val authRes= getUserUseCase()){
                is Authenticated -> {
                    if (authRes.userModel.canWrite) (activity as MainActivity).enableFab()
                    navigateToHomeScreen()
                }
                is AuthError -> {
                    val action= SplashScreenFragmentDirections.actionSplashScreenFragmentToSignInFragment()
                    findNavController().navigate(action)
                }
            }

        }

    }

    private fun navigateToHomeScreen() {
        val action =
            SplashScreenFragmentDirections.actionGlobalHomeFragment()
        findNavController().apply {
            popBackStack(R.id.nav_graph, true)
            navigate(action)
        }
    }

    private fun getBottomNavHeight(): Int {
        val resourceId: Int = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else 0
    }

}
