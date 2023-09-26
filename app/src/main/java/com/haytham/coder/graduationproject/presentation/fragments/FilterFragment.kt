package com.haytham.coder.graduationproject.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.haytham.coder.graduationproject.R
import com.haytham.coder.graduationproject.databinding.FragmentFilterBinding
import com.haytham.coder.graduationproject.domain.viewModel.FilterStudentViewModel
import com.haytham.coder.graduationproject.presentation.activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var dataBinding: FragmentFilterBinding
    private val viewModel: FilterStudentViewModel by viewModels()

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
        dataBinding = FragmentFilterBinding.inflate(inflater).apply {
            lifecycleOwner = this@FilterFragment
            model = viewModel
            yearSpinner.isEnabled = false
            stageSpinner.isEnabled = false
            anyDegreeRadioBtn.callOnClick()

            stateRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.graduateRadioBtn -> {
                        viewModel.isStudent = false
                        yearSpinner.isEnabled = true
                        stageSpinner.isEnabled = false
                        stageSpinner.setSelection(0)
                    }
                    R.id.studentRadioBtn -> {
                        viewModel.isStudent = true
                        stageSpinner.isEnabled = true
                        yearSpinner.isEnabled = false
                        yearSpinner.setSelection(0)
                    }
                    else -> {
                        viewModel.isStudent = null
                        yearSpinner.isEnabled = false
                        stageSpinner.isEnabled = false
                        yearSpinner.setSelection(0)
                        stageSpinner.setSelection(0)
                    }
                }

            }

            degreeRadioGroup.tableOnClickListener = {
                when (degreeRadioGroup.checkedRadioButtonId) {
                    R.id.bachelorRadioBtn -> {
                        viewModel.degree = getString(R.string.bachelor)
                    }
                    R.id.masterRadioBtn -> {
                        viewModel.degree = getString(R.string.master)
                    }
                    R.id.doctoralRadioBtn -> {
                        viewModel.degree = getString(R.string.doctoral)
                    }
                    else -> {
                        viewModel.degree = null
                    }
                }
            }

            stayInCollegeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.yesStayInCollegeRadioBtn -> {
                        viewModel.inCollegeResidence = true
                    }
                    R.id.noStayInCollegeRadioBtn -> {
                        viewModel.inCollegeResidence = false
                    }
                    else -> {
                        viewModel.inCollegeResidence = null
                    }
                }
            }

            studyTimeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.yesStudyTimeRadioBtn -> {
                        viewModel.eveningCollege = true
                    }
                    R.id.noStudyTimeRadioBtn -> {
                        viewModel.eveningCollege = false
                    }
                    else -> {
                        viewModel.eveningCollege = null
                    }
                }
            }

            stageSpinner.onItemSelectedListener = this@FilterFragment
            yearSpinner.onItemSelectedListener = this@FilterFragment
            citySpinner.onItemSelectedListener = this@FilterFragment
            departmentSpinner.onItemSelectedListener = this@FilterFragment
            branchSpinner.onItemSelectedListener = this@FilterFragment

        }

        return dataBinding.root
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        viewModel.apply {
            when (parent.id) {
                R.id.stageSpinner -> {
                    stage = if (id == 0L) null else id.toInt()
                }
                R.id.yearSpinner -> graduationYear =
                    if (id == 0L) null else dataBinding.yearSpinner.selectedItem.toString().toInt()
                R.id.citySpinner -> cityId = if (id == 0L) null else id.toInt() - 1
                R.id.departmentSpinner -> selectedDepartment.value = if (id == 0L) null else
                    dataBinding.departmentSpinner.selectedItem.toString()
                R.id.branchSpinner -> branchName = if (id == 0L) null else branches.value?.find {
                    it.departmentName == selectedDepartment.value && it.branchName == dataBinding.branchSpinner.selectedItem.toString()
                }?.branchName
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onDestroy() {
        viewModel.updateFilter()
        super.onDestroy()
    }

}
