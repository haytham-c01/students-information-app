package com.haytham.coder.graduationproject.utils

import android.util.Patterns

private const val MINI_PASSWORD_LENGTH= 6

val String.isValidEmail get() = isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
val String.isValidPass get() = isNotBlank() && length >= MINI_PASSWORD_LENGTH
