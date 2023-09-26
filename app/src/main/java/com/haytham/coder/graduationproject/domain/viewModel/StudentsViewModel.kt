package com.haytham.coder.graduationproject.domain.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.haytham.coder.graduationproject.domain.usecase.contract.IDeleteStudentUseCase
import com.haytham.coder.graduationproject.utils.ApiEmptyResponse
import com.haytham.coder.graduationproject.utils.ApiErrorResponse
import com.haytham.coder.graduationproject.utils.ApiSuccessResponse
import com.haytham.coder.graduationproject.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentsViewModel @Inject constructor(
    application: Application,
    private val deleteStudentUseCase: IDeleteStudentUseCase,
) : AndroidViewModel(application) {


    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private val _errorEvent = MutableLiveData<Event<String>>()

    val isLoading: LiveData<Boolean> = _isLoading
    val errorEvent = _errorEvent

    fun deleteStudent(studentId: String) {
        viewModelScope.launch {
            _isLoading.postValue(true)

            when (val res = deleteStudentUseCase(studentId)) {
                ApiEmptyResponse -> _errorEvent.value = Event("Unknown Error: Empty Response")
                is ApiSuccessResponse -> {}
                is ApiErrorResponse -> _errorEvent.value = Event(res.errorMessage)
            }
            _isLoading.postValue(false)

        }
    }

}