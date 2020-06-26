package com.haytham.coder.graduationproject.presentation.fragments.authentication

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.haytham.coder.graduationproject.databinding.FragmentSignUpBinding
import com.haytham.coder.graduationproject.domain.viewModel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpFragment : BaseAuthFragment() {
    private lateinit var dataBinding: FragmentSignUpBinding
    override val viewModel:SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = FragmentSignUpBinding.inflate(inflater).apply {
            lifecycleOwner= this@SignUpFragment
            viewModel= this@SignUpFragment.viewModel

            backButton.setOnClickListener {
                findNavController().popBackStack()
            }

        }

        super.onCreateView(inflater, container, savedInstanceState)
        return dataBinding.root
    }

}
