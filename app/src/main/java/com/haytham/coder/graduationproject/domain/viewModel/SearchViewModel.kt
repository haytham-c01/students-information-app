package com.haytham.coder.graduationproject.domain.viewModel

import android.app.Application
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.haytham.coder.graduationproject.domain.model.StudentModel
import com.haytham.coder.graduationproject.domain.usecase.contract.ISearchStudentsUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class SearchViewModel @ViewModelInject constructor(
    application: Application,
    private val searchStudentsUseCase: ISearchStudentsUseCase
) : AndroidViewModel(application){

    companion object{
        private const val TAG= "SearchViewModel"
    }

    var keyword:String=""

    var students: MutableLiveData<List<StudentModel>> = MutableLiveData()

    fun onSearchKeywordChanged(){
        Log.d(TAG, "onSearchKeywordChanged")
         viewModelScope.launch {
             searchStudentsUseCase(keyword).collect {
                 students.postValue(it)
                 Log.d(TAG, it.toString())
             }
         }
    }


}