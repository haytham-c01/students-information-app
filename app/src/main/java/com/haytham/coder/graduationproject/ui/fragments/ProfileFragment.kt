package com.haytham.coder.graduationproject.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.haytham.coder.graduationproject.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private lateinit var dataBinding: FragmentProfileBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding= FragmentProfileBinding.inflate(inflater)

        dataBinding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
        return dataBinding.root
    }

}
