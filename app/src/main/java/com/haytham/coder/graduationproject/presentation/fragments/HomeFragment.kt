package com.haytham.coder.graduationproject.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.haytham.coder.graduationproject.R
import com.haytham.coder.graduationproject.presentation.customViews.SpacesItemDecoration
import com.haytham.coder.graduationproject.presentation.adapters.StudentsQuickAdapter
import com.haytham.coder.graduationproject.databinding.FragmentHomeBinding
import com.haytham.coder.graduationproject.presentation.MainActivity


class HomeFragment : Fragment() {
    private lateinit var dataBinding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).showBottomBar()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding= FragmentHomeBinding.inflate(inflater)

        val itemSpacing= resources.getDimension(R.dimen.students_list_half_spacing).toInt()
        dataBinding.studentsRecycler.apply {
            addItemDecoration(
                SpacesItemDecoration(
                    itemSpacing
                )
            )
            adapter= StudentsQuickAdapter()
                .apply {
                val data= MutableList(100){" "}
                setNewInstance(data)
            }
        }
        return dataBinding.root
    }

}
