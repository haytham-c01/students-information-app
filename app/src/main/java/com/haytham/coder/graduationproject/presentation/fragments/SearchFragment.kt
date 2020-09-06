package com.haytham.coder.graduationproject.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.haytham.coder.graduationproject.R
import com.haytham.coder.graduationproject.databinding.FragmentSearchBinding
import com.haytham.coder.graduationproject.domain.model.StudentModel
import com.haytham.coder.graduationproject.domain.usecase.contract.IGetUserUseCase
import com.haytham.coder.graduationproject.domain.viewModel.SearchViewModel
import com.haytham.coder.graduationproject.presentation.activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment() : Fragment() {
    private lateinit var dataBinding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var studentsFragment: StudentsFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        (activity as MainActivity).showBottomBar()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        dataBinding = FragmentSearchBinding.inflate(inflater).apply {
            lifecycleOwner = this@SearchFragment
            model = viewModel

            studentsFragment = StudentsFragment.newInstance()
            childFragmentManager.commit {
                replace(R.id.fragmentContainerView, studentsFragment)
            }
        }

        viewModel.students.observe(
            viewLifecycleOwner,
            ::onSearchCompleted
        )


        dataBinding.searchEditText.addTextChangedListener {
            viewModel.onSearchKeywordChanged()
        }

        return dataBinding.root
    }

    private fun onSearchCompleted(studentsList: List<StudentModel>?) {
        if (studentsList == null) return

        dataBinding.apply {
            progressIndicator.visibility = View.GONE

            errorText.text =
                if (studentsList.isEmpty()) getString(R.string.no_students_found) else ""
            studentsFragment.submitList(studentsList)

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }
}
