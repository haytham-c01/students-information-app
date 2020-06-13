package com.haytham.coder.graduationproject.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.haytham.coder.graduationproject.R
import com.haytham.coder.graduationproject.databinding.FragmentSignInBinding
import com.haytham.coder.graduationproject.ui.MainActivity


class SignInFragment : Fragment() {
    private lateinit var dataBinding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding= FragmentSignInBinding.inflate(inflater).apply {

            loginBtn.setOnClickListener {
                val action= SignInFragmentDirections.actionGlobalHomeFragment()
                findNavController().apply {
                    popBackStack(R.id.nav_graph, true)
                    navigate(action)

                }
            }

            signUpText.setOnClickListener {
                val action=
                    SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
                findNavController().navigate(action)
            }
        }

        return dataBinding.root
    }

}
