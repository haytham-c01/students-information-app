package com.haytham.coder.graduationproject.utils

import android.view.View
import android.view.ViewTreeObserver

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