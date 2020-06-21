package com.haytham.coder.graduationproject.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.haytham.coder.graduationproject.R
import com.haytham.coder.graduationproject.ui.customViews.SpacesItemDecoration
import com.haytham.coder.graduationproject.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {
    private lateinit var dataBinding: FragmentSearchBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding= FragmentSearchBinding.inflate(inflater)

        val itemSpacing= resources.getDimension(R.dimen.students_list_half_spacing).toInt()
        dataBinding.studentsRecycler.apply {
            addItemDecoration(
                SpacesItemDecoration(
                    itemSpacing
                )
            )
//            adapter= StudentsQuickAdapter().apply {
//                val data= MutableList(100){" "}
//                setNewInstance(data)
//            }
        }
        return dataBinding.root
    }

}
