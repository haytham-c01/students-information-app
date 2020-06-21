package com.haytham.coder.graduationproject.ui.adapters

import android.graphics.LinearGradient
import android.graphics.Shader
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.app.ActivityCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText

@BindingAdapter("startColor", "endColor", requireAll = true)
fun TextView.setGradient(@ColorRes startColor: Int,
                         @ColorRes endColor: Int){

    measure(0, 0)
    val textShader: Shader = LinearGradient(
        0f,
        measuredHeight/2f,
        measuredWidth.toFloat(),
        measuredHeight/2f,
        intArrayOf(
            startColor,
            endColor
        ),
        floatArrayOf(0f, 1f),
        Shader.TileMode.CLAMP
    )
    paint.shader = textShader
}

@BindingAdapter("setErrorMsg")
fun TextInputEditText.setErrorMsg(errorMsg: String?){
    error= errorMsg
}