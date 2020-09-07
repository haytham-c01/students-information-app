package com.haytham.coder.graduationproject.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.findFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.haytham.coder.graduationproject.R
import com.haytham.coder.graduationproject.databinding.FragmentHomeBinding
import com.haytham.coder.graduationproject.databinding.ItemStudentBinding
import com.haytham.coder.graduationproject.domain.model.StudentModel
import com.haytham.coder.graduationproject.domain.viewModel.HomeViewModel
import com.haytham.coder.graduationproject.presentation.activity.MainActivity
import com.haytham.coder.graduationproject.utils.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var dataBinding: FragmentHomeBinding
    private lateinit var studentsFragment: StudentsFragment
    private val viewModel: HomeViewModel by viewModels()

    companion object {
        private const val TAG = "HomeFragment"

    }

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
        postponeEnterTransition()

        dataBinding = FragmentHomeBinding.inflate(inflater).apply {
            lifecycleOwner = this@HomeFragment
            viewModel = this@HomeFragment.viewModel

            fragmentContainerView.afterLayoutDrawn {
                startPostponedEnterTransition()
            }

             studentsFragment = StudentsFragment.newInstance()
            childFragmentManager.commit {
                replace(R.id.fragmentContainerView, studentsFragment)
            }
        }

        viewModel.students.observe(
            viewLifecycleOwner,
            ::onStudentsDownloaded
        )

        viewModel.logoutCompleted.observe(
            viewLifecycleOwner,
            ::onLoggedOut
        )

        return dataBinding.root
    }



    private fun onLoggedOut(event: Event<Boolean>) {
        (activity as MainActivity).disableFab()
        event.getContentIfNotHandled()?.run {
            findNavController().apply {
                popBackStack(R.id.nav_graph, true)
                val action = HomeFragmentDirections.actionGlobalSignInFragment(false)
                navigate(action)

            }
        }
    }

    private fun onStudentsDownloaded(studentsResponse: ApiResponse<List<StudentModel>>?) {
        if (studentsResponse == null) return
        Log.d(TAG, studentsResponse.toString())
        dataBinding.apply {

            progressIndicator.visibility = View.GONE
            when (studentsResponse) {
                ApiEmptyResponse -> {
                    errorText.text = getString(R.string.no_students_found)
                    studentsFragment.submitList(listOf())
                }
                is ApiSuccessResponse -> {
                    errorText.text = ""
                    studentsFragment.submitList(studentsResponse.body)
                }

                is ApiErrorResponse -> errorText.text = studentsResponse.errorMessage
            }

        }

    }

}

