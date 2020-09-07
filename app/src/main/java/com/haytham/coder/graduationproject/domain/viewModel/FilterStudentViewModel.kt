package com.haytham.coder.graduationproject.domain.viewModel

import android.app.Application
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.haytham.coder.graduationproject.R
import com.haytham.coder.graduationproject.domain.model.BranchModel
import com.haytham.coder.graduationproject.domain.model.StudentFilter
import com.haytham.coder.graduationproject.domain.usecase.contract.IGetUserBranchesUseCase
import com.haytham.coder.graduationproject.domain.usecase.contract.IUpdateFilterUseCase
import com.haytham.coder.graduationproject.utils.ApiEmptyResponse
import com.haytham.coder.graduationproject.utils.ApiErrorResponse
import com.haytham.coder.graduationproject.utils.ApiSuccessResponse


class FilterStudentViewModel @ViewModelInject constructor(
    application: Application,
    private val updateFilterUseCase: IUpdateFilterUseCase,
    private val getUserBranchesUseCase: IGetUserBranchesUseCase
) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "FilterStudentViewModel"
    }

    private val c= application.applicationContext
    val cities= c.resources.getStringArray(R.array.cities).toMutableList().apply {
        add(0, "Any")
    }
    val years= (20 downTo 0).map { if(it<10 ) "200$it" else "20$it"}.toMutableList().apply {
        add(0, "Any")
    }
    val stages= (1..6).map { it.toString() }.toMutableList().apply {
        add(0, "Any")
    }

    val branches: LiveData<List<BranchModel>> = liveData {

        when (val res = getUserBranchesUseCase()) {
            is ApiSuccessResponse -> emit(res.body)
            is ApiErrorResponse -> {}
            is ApiEmptyResponse -> {}
        }
    }

    val departmentNames = branches.map {
        it.map { it.departmentName }.distinct().toMutableList().apply {
            add(0, "Any")
        }
    }
    val selectedDepartment= MutableLiveData<String?>()
    val branchNames = selectedDepartment.map {
        val list= branches.value?.filter { it.departmentName== selectedDepartment.value }?.map { it.branchName }?: listOf()
            list.toMutableList().apply {
                add(0, "Any")
            }
    }

    var degree: String? = null
    var cityId: Int? = null
    var inCollegeResidence:Boolean?= null
    var eveningCollege:Boolean? = null
    var isStudent: Boolean? = null
    var stage: Int? = null
    var graduationYear: Int? = null
    var branchName: String?= null

    fun updateFilter() {
        val filter= StudentFilter(
            isStudent= isStudent,
            stage= stage,
            graduationYear= graduationYear,
            degree= degree,
            department = selectedDepartment.value,
            branch= branchName,
            cityId= cityId,
            inCollegeResidence= inCollegeResidence,
            isEveningCollege= eveningCollege
        )

        updateFilterUseCase(filter)

        Log.d(TAG, toString())
    }

    override fun toString(): String {
        return "FilterStudentViewModel(degree=$degree, cityId=$cityId, inCollegeResidence=$inCollegeResidence, eveningCollege=$eveningCollege, isStudent=$isStudent, stage=$stage, graduationYear=$graduationYear,  selectedDepartment=${selectedDepartment.value}, branchName=$branchName)"
    }
}