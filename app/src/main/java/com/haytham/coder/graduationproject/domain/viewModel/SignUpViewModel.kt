package com.haytham.coder.graduationproject.domain.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.haytham.coder.graduationproject.domain.usecase.contract.ISignUpUseCase
import com.haytham.coder.graduationproject.utils.AuthError
import com.haytham.coder.graduationproject.utils.Authenticated
import com.haytham.coder.graduationproject.utils.Event
import com.haytham.coder.graduationproject.utils.getSignUpPassErrorMsg
import com.haytham.coder.graduationproject.utils.getSignUpRepeatPassErrorMsg
import com.haytham.coder.graduationproject.utils.getSignUpUsernameErrorMsg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    application: Application,
    private val signUpUseCase: ISignUpUseCase
) : BaseAuthViewModel(application) {

    companion object {
        private const val TAG = "SignInViewModel"
    }

    var username: String = ""
    val usernameError: MutableLiveData<String> = MutableLiveData()

    var repeatPass: String = ""
    val repeatPassError: MutableLiveData<String> = MutableLiveData()

    override fun isDataValid(): Boolean {

        username.getSignUpUsernameErrorMsg()?.let {
            usernameError.value = con.getString(it)
            return false
        }

        if (!super.isDataValid()) return false

        password.getSignUpPassErrorMsg()?.let {
            passwordError.value = con.getString(it)
            return false
        }

        repeatPass.getSignUpRepeatPassErrorMsg(password)?.let {
            repeatPassError.value = con.getString(it)
            return false
        }

        return true
    }

    override fun authenticate() {
        if (isDataValid()) {
            viewModelScope.launch {
                _isLoading.postValue(true)
                when (val authRes =
                    signUpUseCase(username.trim(), email.toLowerCase().trim(), password)) {
                    is Authenticated -> _authenticatedEvent.value = Event(true)
                    is AuthError -> _authErrorEvent.value = Event(authRes.errorMessage)
                }
                _isLoading.postValue(false)
            }
        }
    }

}