package com.haytham.coder.graduationproject.presentation.adapters

import android.graphics.Bitmap
import android.graphics.LinearGradient
import android.graphics.Shader
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.databinding.BindingAdapter
import com.facebook.drawee.view.SimpleDraweeView
import com.google.android.material.textfield.TextInputEditText
import com.haytham.coder.graduationproject.R
import kotlinx.android.synthetic.main.fragment_profile.view.*

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

@BindingAdapter("setCity")
fun TextView.setCity(cityId: Int){
    text= resources.getStringArray(R.array.cities)[cityId]
}

@BindingAdapter("android:setList")
fun <T>Spinner.setSpinnerList( list:List<T>?) {
    if(list == null) return
    ArrayAdapter(
        context,
        android.R.layout.simple_spinner_item, list
    ).apply {
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter= this
    }
}


@BindingAdapter("android:layoutMarginTop")
fun View.setLayoutMarginTop(dimen: Int) {
    val layoutParams = layoutParams as ViewGroup.MarginLayoutParams
    layoutParams.topMargin = dimen
    setLayoutParams(layoutParams)
}

@BindingAdapter("android:layoutMarginBottom")
fun View.setLayoutMarginBottom(dimen: Int) {
    val layoutParams = layoutParams as ViewGroup.MarginLayoutParams
    layoutParams.bottomMargin = dimen
    setLayoutParams(layoutParams)
}

@BindingAdapter("android:setBitmap")
fun ImageView.setBitmap(bitmap: Bitmap?) {
    setImageBitmap(bitmap)
}

@BindingAdapter("android:imageUrl")
fun SimpleDraweeView.setImageUrl(url: String?) {
    setImageURI(url)
}