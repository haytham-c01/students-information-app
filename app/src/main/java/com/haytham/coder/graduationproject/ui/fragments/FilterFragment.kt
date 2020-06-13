package com.haytham.coder.graduationproject.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.haytham.coder.graduationproject.R
import com.haytham.coder.graduationproject.databinding.FragmentFilterBinding
import com.haytham.coder.graduationproject.ui.SpacesItemDecoration
import com.haytham.coder.graduationproject.databinding.FragmentSearchBinding
import com.haytham.coder.graduationproject.ui.MainActivity


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
