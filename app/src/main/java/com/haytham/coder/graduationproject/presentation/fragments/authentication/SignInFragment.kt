package com.haytham.coder.graduationproject.presentation.fragments.authentication

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.haytham.coder.graduationproject.R
import com.haytham.coder.graduationproject.databinding.FragmentSignInBinding
import com.haytham.coder.graduationproject.domain.viewModel.SignInViewModel
import com.haytham.coder.graduationproject.presentation.activity.MainActivity
import com.haytham.coder.graduationproject.utils.afterLayoutDrawn
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseAuthFragment() {
    private lateinit var dataBinding: FragmentSignInBinding
    private val args: SignInFragmentArgs by navArgs()
    private var firstRun: Boolean = true
    override val viewModel: SignInViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        (activity as MainActivity).hideBottomBar()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (!firstRun) postponeEnterTransition()
        setupBinding(inflater)


        super.onCreateView(inflater, container, savedInstanceState)
        return dataBinding.root
    }

    private fun setupBinding(inflater: LayoutInflater) {
        dataBinding = FragmentSignInBinding.inflate(inflater).apply {
            lifecycleOwner = this@SignInFragment
            viewModel = this@SignInFragment.viewModel


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
    }

    override fun onStart() {
        handleTransitionAnimation()
        super.onStart()
    }

    private fun handleTransitionAnimation() {
        if (!firstRun || !args.showAnimation) {
            setZeroTransitionTime()
        }

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
        dataBinding.container.getTransition(R.id.loginAppearanceTransition).duration = 1
    }

}
