package com.haytham.coder.graduationproject.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haytham.coder.graduationproject.R
import com.haytham.coder.graduationproject.utils.isValidEmail
import com.haytham.coder.graduationproject.utils.isValidPass

class SignInViewModel(application: Application) :AndroidViewModel(application){
    companion object{
        private const val TAG= "SignInViewModel"
    }

    private val con= application.applicationContext
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
           // login

            Log.i(TAG, "logged with: $email - $password")
        }
    }

}