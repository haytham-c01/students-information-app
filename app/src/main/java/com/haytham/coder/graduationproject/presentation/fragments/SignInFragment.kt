package com.haytham.coder.graduationproject.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.haytham.coder.graduationproject.R
import com.haytham.coder.graduationproject.databinding.FragmentSignInBinding
import com.haytham.coder.graduationproject.domain.viewModel.SignInViewModel
import com.haytham.coder.graduationproject.utils.afterLayoutDrawn
import com.haytham.coder.graduationproject.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {
    private lateinit var dataBinding: FragmentSignInBinding
    private val args: SignInFragmentArgs by navArgs()
    private var firstRun: Boolean = true
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if(!firstRun) postponeEnterTransition()

        dataBinding = FragmentSignInBinding.inflate(inflater).apply {
            viewModel = this@SignInFragment.viewModel
            lifecycleOwner = this@SignInFragment

            signUpText.setOnClickListener {
                val extras = FragmentNavigatorExtras(
                    appTitleText to appTitleText.transitionName,
                    logo to logo.transitionName,
                    email to email.transitionName,
                    password to password.transitionName,
                    loginBtn to loginBtn.transitionName
                )

                val action =
                    SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
                findNavController().navigate(action, extras)
            }

        }

        setupViewModel()
        return dataBinding.root
    }

    private fun setupViewModel() {
        viewModel.apply {
            loggedInEvent.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let {
                    navigateToHomeScreen()
                }
            }

            loginErrorEvent.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let {
                    showLoginError(it)
                }
            }
        }
    }

    private fun showLoginError(it: String) {
        dataBinding.container.showSnackBar(it)
    }

    private fun navigateToHomeScreen() {
        val action = SignInFragmentDirections.actionGlobalHomeFragment()
        findNavController().apply {
            popBackStack(R.id.nav_graph, true)
            navigate(action)
        }
    }

    override fun onStart() {
        handleTransitionAnimation()
        super.onStart()
    }

    private fun handleTransitionAnimation() {
        if (!firstRun || !args.showAnimation) { setZeroTransitionTime() }

        dataBinding.container.afterLayoutDrawn {
            startTransitionAnimation()
            firstRun = false
        }
    }

    private fun startTransitionAnimation() {
        dataBinding.container.transitionToEnd()
        startPostponedEnterTransition()
    }

    private fun setZeroTransitionTime() {
        dataBinding.container.getTransition(R.id.loginAppearanceTransition).duration = 0
    }

}
