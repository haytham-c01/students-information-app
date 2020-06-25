package com.haytham.coder.graduationproject.utils

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.core.view.get
import com.google.android.material.snackbar.Snackbar
import com.haytham.coder.graduationproject.R
import com.orhanobut.logger.Logger

fun View.afterLayoutDrawn(action: ()->Unit){
        viewTreeObserver.addOnGlobalLayoutListener(
            object:ViewTreeObserver.OnGlobalLayoutListener{
                override fun onGlobalLayout() {
                    action()
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            }
        )
}

fun ViewGroup.showSnackBar(msg:String, isError: Boolean= true){
    val snackBar = Snackbar.make(this, msg, Snackbar.LENGTH_LONG)
    val snackView: ViewGroup = snackBar.view as ViewGroup
    //snackView.setBackgroundColor(Color.WHITE)
    (snackView[0] as ViewGroup).forEach {
        //Logger.i("type " + it.javaClass.name)
        if (it is TextView) {
            it.setTextColor(if(isError) ContextCompat.getColor(context, R.color.colorErrOnBlack) else Color.rgb(0, 120, 0))
        }
    }
    snackBar.show()
}