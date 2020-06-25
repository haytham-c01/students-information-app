package com.haytham.coder.graduationproject.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.haytham.coder.graduationproject.databinding.FragmentFilterBinding


class FilterFragment : Fragment() {
    private lateinit var dataBinding: FragmentFilterBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding= FragmentFilterBinding.inflate(inflater)

        return dataBinding.root
    }

}
