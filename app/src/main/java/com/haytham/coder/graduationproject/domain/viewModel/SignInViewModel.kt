package com.haytham.coder.graduationproject.domain.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.haytham.coder.graduationproject.domain.usecase.contract.ILoginUseCase
import com.haytham.coder.graduationproject.utils.AuthError
import com.haytham.coder.graduationproject.utils.Authenticated
import com.haytham.coder.graduationproject.utils.Event
import com.haytham.coder.graduationproject.utils.getLoginPassErrorMsg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    application: Application,
    private val loginUseCase: ILoginUseCase,
) : BaseAuthViewModel(application) {

    companion object {
        private const val TAG = "SignInViewModel"
    }

    init {
//        viewModelScope.launch {
//            val authRes= getUserUseCase()
//            if(authRes is Authenticated){
//                _authenticatedEvent.value= Event(authRes.userModel.canWrite)
//            }
//        }
    }

    override fun isDataValid(): Boolean {
        if (!super.isDataValid()) return false

        password.getLoginPassErrorMsg()?.let {
            passwordError.value = con.getString(it)
            return false
        }

        return true
    }

    override fun authenticate() {
        if (isDataValid()) {
            Log.d(TAG, "inside authenticate");
            viewModelScope.launch {
                _isLoading.postValue(true)
                when (val authRes = loginUseCase(email.toLowerCase().trim(), password)) {
                    is Authenticated -> _authenticatedEvent.value =
                        Event(authRes.userModel.canWrite)

                    is AuthError -> _authErrorEvent.value = Event(authRes.errorMessage)
                }

                _isLoading.postValue(false)
            }
        }
    }

}