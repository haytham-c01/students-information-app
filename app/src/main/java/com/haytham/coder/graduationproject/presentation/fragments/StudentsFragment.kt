package com.haytham.coder.graduationproject.presentation.fragments

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.haytham.coder.graduationproject.R
import com.haytham.coder.graduationproject.databinding.FragmentStudentsBinding
import com.haytham.coder.graduationproject.databinding.ItemStudentBinding
import com.haytham.coder.graduationproject.domain.model.StudentModel
import com.haytham.coder.graduationproject.domain.viewModel.StudentsViewModel
import com.haytham.coder.graduationproject.presentation.activity.MainActivity
import com.haytham.coder.graduationproject.presentation.adapters.StudentsAdapter
import com.haytham.coder.graduationproject.presentation.customViews.SpacesItemDecoration
import com.haytham.coder.graduationproject.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudentsFragment(
) : Fragment() {
    private lateinit var dataBinding: FragmentStudentsBinding
    private val viewModel: StudentsViewModel by viewModels()

    companion object {
        private const val EXTRA_STUDENTS = "extraStudents"

        fun newInstance() = StudentsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).apply {
            showBottomBar()
            setupStudentAddFab()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = FragmentStudentsBinding.inflate(inflater).apply {
            val itemSpacing = resources.getDimension(R.dimen.students_list_half_spacing).toInt()

            lifecycleOwner = this@StudentsFragment
            studentsRecycler.addItemDecoration(
                SpacesItemDecoration(
                    itemSpacing
                )
            )

            val canWrite= (activity as MainActivity).isFabEnabled
            studentsRecycler.adapter = StudentsAdapter(canWrite).apply {
                phoneIconPressed = ::onPhoneIconPressed
                copyIconPressed = ::onCopyIconPressed
                studentImagePressed = ::onProfileImagePressed
                deleteIconPressed = ::onDeleteStudent

            }

            viewModel.errorEvent.observe(viewLifecycleOwner){
                it?.getContentIfNotHandled()?.let {
                    (dataBinding.root.rootView as ViewGroup).showSnackBar(it)
                }
            }

        }

        return dataBinding.root
    }

    fun submitList(students: List<StudentModel>){
        (dataBinding.studentsRecycler.adapter as StudentsAdapter).submitList(students.toMutableList())
    }
    private fun onDeleteStudent(studentId:String){
        viewModel.deleteStudent(studentId)
    }

    private fun onProfileImagePressed(  studentBinding: ItemStudentBinding, studentModel: StudentModel) {

        studentBinding.apply {
            val extras = FragmentNavigatorExtras(
                studentImage to studentImage.transitionName,
                studentName to studentName.transitionName,
            )

            val action =
                HomeFragmentDirections.actionGlobalProfileFragment(studentModel)
            findNavController().navigate(action, extras)
        }
    }

    private fun checkIfAlreadyHavePermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CALL_PHONE
        )
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestForSpecificPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.CALL_PHONE,
            ),
            101
        )
    }

    private fun onPhoneIconPressed(phoneNumber: String) {
        if (!checkIfAlreadyHavePermission()) {
            requestForSpecificPermission()
        } else {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:$phoneNumber")
            requireContext().startActivity(intent)
        }
    }

    private fun onCopyIconPressed(phoneNumber: String) {
        val clipboard =
            requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val clip = ClipData.newPlainText(phoneNumber, phoneNumber)
        clipboard?.setPrimaryClip(clip)
        Toast.makeText(requireContext(), "Phone number copied", Toast.LENGTH_SHORT).show()
    }
}

