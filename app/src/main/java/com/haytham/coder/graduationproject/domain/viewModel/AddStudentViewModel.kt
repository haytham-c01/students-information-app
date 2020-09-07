package com.haytham.coder.graduationproject.domain.viewModel

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.haytham.coder.graduationproject.R
import com.haytham.coder.graduationproject.data.model.NetworkStudent
import com.haytham.coder.graduationproject.data.remoteDataSource.implementation.FirestoreBranchService
import com.haytham.coder.graduationproject.domain.model.BranchModel
import com.haytham.coder.graduationproject.domain.usecase.contract.IAddStudentUseCase
import com.haytham.coder.graduationproject.domain.usecase.contract.IGetUserBranchesUseCase
import com.haytham.coder.graduationproject.utils.ApiEmptyResponse
import com.haytham.coder.graduationproject.utils.ApiErrorResponse
import com.haytham.coder.graduationproject.utils.ApiSuccessResponse
import com.haytham.coder.graduationproject.utils.Event
import kotlinx.coroutines.launch


class AddStudentViewModel @ViewModelInject constructor(
    application: Application,
    private val addStudentUseCase: IAddStudentUseCase,
    private val getUserBranchesUseCase: IGetUserBranchesUseCase
) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "AddStudentViewModel"
    }

    private val c= application.applicationContext
    val cities= c.resources.getStringArray(R.array.cities).toList()

    val years= (20 downTo 0).map { if(it<10 ) "200$it" else "20$it"}
    val stages= (1..6).toList()

    var studentName: String = ""
    var studentPhone: String = ""
    var studentEmail: String = ""
    var degree: String = c.getString(R.string.bachelor)
    var cityId: Int = 0
    var inCollegeResidence = false
    var eveningCollege = false
    var isStudent: Boolean = true
    var stage: Int = 1
    var graduationYear: Int = 2020
    var branchId: String = ""
    var studentImage = MutableLiveData<Bitmap>()



    private var _studentNameError:MutableLiveData<String> = MutableLiveData()
    private var _studentPhoneError:MutableLiveData<String> = MutableLiveData()
    private var _studentEmailError:MutableLiveData<String> = MutableLiveData()
    private val _isLoading:MutableLiveData<Boolean> = MutableLiveData(false)
    private val _errorEvent= MutableLiveData<Event<String>>()
    private val _studentAddedEvent= MutableLiveData<Event<Boolean>>()

    val isLoading: LiveData<Boolean> = _isLoading
    val errorEvent= _errorEvent
    val studentAddedEvent= _studentAddedEvent
    var studentNameError:LiveData<String> = _studentNameError
    var studentPhoneError:LiveData<String> = _studentPhoneError
    var studentEmailError:LiveData<String> = _studentEmailError


    val branches: LiveData<List<BranchModel>> = liveData {

        when (val res = getUserBranchesUseCase()) {
             is ApiSuccessResponse -> emit(res.body)
            is ApiErrorResponse -> {}
            is ApiEmptyResponse -> {}
        }
    }

    val departmentNames = branches.map {
        it.map { it.departmentName }.distinct()
    }
    val selectedDepartment= MutableLiveData<String>()
    val branchNames = selectedDepartment.map { branches.value?.filter { it.departmentName== selectedDepartment.value }?.map { it.branchName }?: listOf() }




    fun addStudent() {

        if(studentName.isBlank()){
            _studentNameError.value = "Username is required!"
            return
        }
        if(studentPhone.isBlank()){
            _studentPhoneError.value = "Phone is required!";
            return
        }
        if(studentEmail.isBlank()){
            _studentEmailError.value ="Email is required!"
            return
        }

        if(branchId.isBlank()){
            _errorEvent.value= Event("Branch is required!")
            return
        }
        if(studentImage.value == null){
            _errorEvent.value= Event("Profile image must be selected!")
            return
        }

        viewModelScope.launch {
            _isLoading.postValue(true)
            val networkStudent= NetworkStudent(
                studentId = null,
                studentName = studentName.trim(),
                phone = studentPhone.trim(),
                email = studentEmail.trim(),
                stage = if(isStudent) stage else null,
                graduationYear = if(isStudent) null else graduationYear,
                cityId = cityId.toString(),
                degree = degree,
                inCollegeResidence = inCollegeResidence,
                eveningCollege = eveningCollege,
                branch = Firebase.firestore.collection(FirestoreBranchService.BRANCH_COL).document(branchId)
            )
            Log.d(TAG, networkStudent.toString())
            when(val res= addStudentUseCase(networkStudent, studentImage.value!!)){
                ApiEmptyResponse -> _errorEvent.value= Event("Unknown Error: Empty Response")
                is ApiSuccessResponse -> _studentAddedEvent.value= Event(true)
                is ApiErrorResponse -> _errorEvent.value= Event(res.errorMessage)
            }
            _isLoading.postValue(false)

        }
    }

    override fun toString(): String {
        return "AddStudentViewModel(addStudentUseCase=$addStudentUseCase, getBranchesUseCase=$getUserBranchesUseCase, c=$c, cities=$cities, years=$years, stages=$stages, studentName='$studentName', studentPhone='$studentPhone', studentEmail='$studentEmail', degree='$degree', cityId=$cityId, inCollegeResidence=$inCollegeResidence, eveningCollege=$eveningCollege, isStudent=$isStudent, stage=$stage, graduationYear=$graduationYear, branchId='$branchId', studentNameError=$_studentNameError, studentPhoneError=$_studentPhoneError, studentEmailError=$_studentEmailError, branches=$branches, departmentNames=$departmentNames, selectedDepartment=$selectedDepartment, branchNames=$branchNames)"
    }


}