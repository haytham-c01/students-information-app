package com.haytham.coder.graduationproject.presentation.fragments

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.haytham.coder.graduationproject.databinding.FragmentSignInBinding
import com.haytham.coder.graduationproject.databinding.FragmentSignUpBinding


class SignUpFragment : Fragment() {
    private lateinit var dataBinding: FragmentSignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = FragmentSignUpBinding.inflate(inflater)

        dataBinding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        return dataBinding.root
    }
}
