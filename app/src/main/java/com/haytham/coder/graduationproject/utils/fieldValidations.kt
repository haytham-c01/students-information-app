package com.haytham.coder.graduationproject.utils

import android.util.Patterns
import com.haytham.coder.graduationproject.R

private const val MINI_PASSWORD_LENGTH = 6
private const val MIN_USERNAME_LENGTH = 2

private val String.isValidEmail get() =
    Patterns.EMAIL_ADDRESS
        .matcher(this)
        .matches()

fun String.getEmailErrorMsg() =
    if (isNotBlank() &&
        isValidEmail
    ) null
    else R.string.invalid_email



fun String.getLoginPassErrorMsg() =
    if (isNotBlank() && length >= MINI_PASSWORD_LENGTH) null
    else R.string.invalid_password


fun String.getSignUpUsernameErrorMsg() = when {
    isBlank() -> R.string.field_required
    length < MIN_USERNAME_LENGTH -> R.string.min_username_error
    else -> null
}

fun String.getSignUpPassErrorMsg() = when{
    isBlank() -> R.string.field_required
    length < MINI_PASSWORD_LENGTH -> R.string.min_password_error
    else -> null
}

fun String.getSignUpRepeatPassErrorMsg(pass: String) = when{
    isBlank() -> R.string.field_required
        this != pass-> R.string.different_repeat_passe_error
        else -> null
}


