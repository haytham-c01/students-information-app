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
import com.haytham.coder.graduationproject.domain.usecase.contract.IDeleteStudentUseCase
import com.haytham.coder.graduationproject.domain.usecase.contract.IGetBranchesUseCase
import com.haytham.coder.graduationproject.domain.usecase.contract.IGetUserUseCase
import com.haytham.coder.graduationproject.utils.ApiEmptyResponse
import com.haytham.coder.graduationproject.utils.ApiErrorResponse
import com.haytham.coder.graduationproject.utils.ApiSuccessResponse
import com.haytham.coder.graduationproject.utils.Event
import kotlinx.coroutines.launch


class StudentsViewModel @ViewModelInject constructor(
    application: Application,
    private val deleteStudentUseCase: IDeleteStudentUseCase,
) : AndroidViewModel(application) {



    private val _isLoading:MutableLiveData<Boolean> = MutableLiveData(false)
    private val _errorEvent= MutableLiveData<Event<String>>()

    val isLoading: LiveData<Boolean> = _isLoading
    val errorEvent= _errorEvent

    fun deleteStudent(studentId:String) {
        viewModelScope.launch {
            _isLoading.postValue(true)

            when(val res= deleteStudentUseCase(studentId)){
                ApiEmptyResponse -> _errorEvent.value= Event("Unknown Error: Empty Response")
                is ApiSuccessResponse -> {}
                is ApiErrorResponse -> _errorEvent.value= Event(res.errorMessage)
            }
            _isLoading.postValue(false)

        }
    }


}