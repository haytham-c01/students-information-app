package com.haytham.coder.graduationproject.domain.viewModel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.haytham.coder.graduationproject.R
import com.haytham.coder.graduationproject.domain.usecase.contract.LoginUseCase
import com.haytham.coder.graduationproject.utils.*
import kotlinx.coroutines.launch


class SignInViewModel @ViewModelInject constructor(
    application: Application,
    private val loginUseCase: LoginUseCase
) : BaseAuthViewModel(application){

    companion object{
        private const val TAG= "SignInViewModel"
    }

    override fun isDataValid(): Boolean {
        if(!super.isDataValid()) return false

        password.getLoginPassErrorMsg()?.let {
            passwordError.value = con.getString(it)
            return false
        }

        return true
    }

    override fun authenticate(){
        if(isDataValid()){
            viewModelScope.launch {
                when(val authRes= loginUseCase(email, password)){
                  is Authenticated -> _authenticatedEvent.value= Event(true)
                  is AuthError -> _authErrorEvent.value= Event(authRes.errorMessage)
                }
            }
        }
    }

}