package com.haytham.coder.graduationproject.domain.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.haytham.coder.graduationproject.R
import com.haytham.coder.graduationproject.utils.*

abstract class BaseAuthViewModel (
    application: Application
) : AndroidViewModel(application){
    protected val con:Context= application.applicationContext
    protected val _authenticatedEvent= MutableLiveData<Event<Boolean>>()
    protected val _authErrorEvent= MutableLiveData<Event<String>>()

    val authenticatedEvent= _authenticatedEvent
    val authErrorEvent= _authErrorEvent

    var email:String= ""
    var password:String= ""

    val emailError: MutableLiveData<String> = MutableLiveData()
    val passwordError: MutableLiveData<String> = MutableLiveData()


    protected open fun isDataValid(): Boolean {

        email.getEmailErrorMsg()?.let {
            emailError.value = con.getString(it)
            return false
        }

        return true
    }

    abstract fun authenticate()
}