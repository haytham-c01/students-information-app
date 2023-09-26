package com.haytham.coder.graduationproject.domain.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.haytham.coder.graduationproject.domain.model.StudentModel
import com.haytham.coder.graduationproject.domain.usecase.contract.IGetBranchStudentsUseCase
import com.haytham.coder.graduationproject.domain.usecase.contract.ILogoutUseCase
import com.haytham.coder.graduationproject.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val getBranchStudentsUseCase: IGetBranchStudentsUseCase,
    private val logoutUseCase: ILogoutUseCase
) : AndroidViewModel(application){

    companion object{
        private const val TAG= "HomeView Model"
    }

    private val _logoutCompleted= MutableLiveData<Event<Boolean>>()
    val logoutCompleted= _logoutCompleted

    val students: LiveData<ApiResponse<List<StudentModel>>> = liveData{

        // TODO: get branch id from user
        val studentsRes= getBranchStudentsUseCase()
        Log.d(TAG, studentsRes.toString())

        emitSource(studentsRes.asLiveData())
    }

    fun logout(){
       viewModelScope.launch {
           logoutUseCase()
           logoutCompleted.postValue(Event(true))
       }
    }


}