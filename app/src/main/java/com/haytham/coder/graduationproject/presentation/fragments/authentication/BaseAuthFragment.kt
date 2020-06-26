package com.haytham.coder.graduationproject.presentation.fragments.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.haytham.coder.graduationproject.R
import com.haytham.coder.graduationproject.domain.viewModel.BaseAuthViewModel
import com.haytham.coder.graduationproject.utils.showSnackBar

abstract class BaseAuthFragment : Fragment() {
    protected abstract val viewModel: BaseAuthViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupViewModel()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun setupViewModel() {
        viewModel.apply {
            authenticatedEvent.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let {
                    navigateToHomeScreen()
                }
            }

            authErrorEvent.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let {
                    showAuthenticationError(it)
                }
            }
        }
    }

    private fun showAuthenticationError(it: String) {
        (view as ViewGroup).showSnackBar(it)
    }

    private fun navigateToHomeScreen() {
        val action =
            SignInFragmentDirections.actionGlobalHomeFragment()
        findNavController().apply {
            popBackStack(R.id.nav_graph, true)
            navigate(action)
        }
    }
}