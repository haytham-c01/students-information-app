package com.haytham.coder.graduationproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.haytham.coder.graduationproject.databinding.FragmentHomeBinding
import com.haytham.coder.graduationproject.databinding.FragmentSignInBinding


class SignInFragment : Fragment() {
    private lateinit var dataBinding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding= FragmentSignInBinding.inflate(inflater).apply {

            loginBtn.setOnClickListener {
                val action= SignInFragmentDirections.actionSignInFragmentToHomeFragment()
                findNavController().navigate(action)
            }

            signUpText.setOnClickListener {
                val action= SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
                findNavController().navigate(action)
            }
        }

        return dataBinding.root
    }

}
