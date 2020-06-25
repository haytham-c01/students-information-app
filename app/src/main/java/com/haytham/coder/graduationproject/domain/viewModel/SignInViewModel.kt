package com.haytham.coder.graduationproject.domain.viewModel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.haytham.coder.graduationproject.R
import com.haytham.coder.graduationproject.domain.usecase.contract.LoginUseCase
import com.haytham.coder.graduationproject.utils.*
import kotlinx.coroutines.launch


class SignInViewModel @ViewModelInject constructor(
    application: Application,
    private val loginUseCase: LoginUseCase
)
    : AndroidViewModel(application){

    companion object{
        private const val TAG= "SignInViewModel"
    }

    private val con= application.applicationContext

    private val _loggedInEvent= MutableLiveData<Event<Boolean>>()
    private val _loginErrorEvent= MutableLiveData<Event<String>>()

    val loggedInEvent= _loggedInEvent
    val loginErrorEvent= _loginErrorEvent

    var email:String= ""
    var password:String= ""

    var emailError:MutableLiveData<String> = MutableLiveData()
    var passwordError:MutableLiveData<String> = MutableLiveData()



    fun login(){
        var isDataValid= true

        if(!email.isValidEmail){
            emailError.value = con.getString(R.string.invalid_email)
            isDataValid= false
        }
        if(!password.isValidPass){
            passwordError.value= con.getString(R.string.invalid_password)
            isDataValid= false
        }

        if(isDataValid){
            viewModelScope.launch {
                when(val authRes= loginUseCase(email, password)){
                  is Authenticated -> _loggedInEvent.value= Event(true)
                  is AuthError -> _loginErrorEvent.value= Event(authRes.errorMessage)
                }
            }
        }
    }

}