package com.haytham.coder.graduationproject.presentation.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.haytham.coder.graduationproject.R
import com.haytham.coder.graduationproject.databinding.FragmentAddStudentBinding
import com.haytham.coder.graduationproject.domain.viewModel.AddStudentViewModel
import com.haytham.coder.graduationproject.presentation.activity.MainActivity
import com.haytham.coder.graduationproject.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_student.*


@AndroidEntryPoint
class AddStudentFragment(
) : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var dataBinding: FragmentAddStudentBinding
    private val viewModel: AddStudentViewModel by viewModels()

    companion object {
        private const val TAG = "AddStudentFragment"
        private const val PICK_IMAGE = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).hideBottomBar()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = FragmentAddStudentBinding.inflate(inflater).apply {
            lifecycleOwner = this@AddStudentFragment
            model = viewModel

            yearSpinner.isEnabled= false
            stateRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                val isStudent = checkedId == R.id.studentRadioBtn
                viewModel.isStudent = isStudent
                stageSpinner.isEnabled = isStudent
                yearSpinner.isEnabled = !isStudent
            }

            stayInCollegeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                viewModel.inCollegeResidence = checkedId == R.id.yesRadioBtn
            }

            studyTimeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                viewModel.eveningCollege = checkedId == R.id.eveningRadioBtn
            }

            degreeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                viewModel.degree = when (checkedId) {
                    R.id.bachelorRadioBtn -> requireContext().getString(R.string.bachelor)
                    R.id.masterRadioBtn -> requireContext().getString(R.string.master)
                    R.id.doctoralRadioBtn -> requireContext().getString(R.string.doctoral)
                    else -> ""
                }
            }

            stageSpinner.onItemSelectedListener = this@AddStudentFragment
            yearSpinner.onItemSelectedListener = this@AddStudentFragment
            citySpinner.onItemSelectedListener = this@AddStudentFragment
            departmentSpinner.onItemSelectedListener = this@AddStudentFragment
            branchSpinner.onItemSelectedListener = this@AddStudentFragment

            viewModel.errorEvent.observe(viewLifecycleOwner)
            { event ->
                event?.getContentIfNotHandled()?.let { onError(it) }
            }

            viewModel.studentAddedEvent.observe(viewLifecycleOwner) {
                it?.getContentIfNotHandled()?.let { studentAdded ->
                    if (studentAdded) findNavController().popBackStack()
                }
            }

            studentImage.setOnClickListener { selectImage() }
        }
        return dataBinding.root
    }

    private fun selectImage() {
        val getIntent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }

        val pickIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        ).apply {
            type = "image/*";
        }

        val chooserIntent = Intent.createChooser(getIntent, "Select Image")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

        startActivityForResult(
            chooserIntent,
            PICK_IMAGE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val imageBitmap= uriToBitmap(data.data!!)
            viewModel.studentImage.value= imageBitmap
        }
    }

    private fun uriToBitmap(uri: Uri): Bitmap {
        return MediaStore.Images.Media.getBitmap(activity?.contentResolver, uri)
    }

    private fun onError(errorMSg: String) {
        (dataBinding.root as ViewGroup).showSnackBar(errorMSg, true)
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        viewModel.apply {
            when (parent.id) {
                R.id.stageSpinner -> stage = id.toInt() + 1
                R.id.yearSpinner -> graduationYear = yearSpinner.selectedItem.toString().toInt()
                R.id.citySpinner -> cityId = id.toInt()
                R.id.departmentSpinner -> selectedDepartment.value =
                    departmentSpinner.selectedItem.toString()
                R.id.branchSpinner -> branchId = branches.value?.find {
                    it.departmentName == selectedDepartment.value && it.branchName == branchSpinner.selectedItem.toString()
                }?.branchId ?: ""
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).showBottomBar()
    }

}

