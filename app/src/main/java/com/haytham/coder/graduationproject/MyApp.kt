package com.haytham.coder.graduationproject

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.firebase.FirebaseApp
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        AndroidThreeTen.init(this)
        Fresco.initialize(this)
    }
}