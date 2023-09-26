package com.haytham.coder.graduationproject.domain.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.haytham.coder.graduationproject.domain.model.StudentModel
import com.haytham.coder.graduationproject.domain.usecase.contract.ISearchStudentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    application: Application,
    private val searchStudentsUseCase: ISearchStudentsUseCase
) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "SearchViewModel"
    }

    var keyword: String = ""

    var students: MutableLiveData<List<StudentModel>> = MutableLiveData()

    fun onSearchKeywordChanged() {
        Log.d(TAG, "onSearchKeywordChanged")
        viewModelScope.launch {
            searchStudentsUseCase(keyword).collect {
                students.postValue(it)
                Log.d(TAG, it.toString())
            }
        }
    }


}