package com.haytham.coder.graduationproject.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.haytham.coder.graduationproject.R
import java.util.*
import kotlin.concurrent.schedule


class SplashScreenFragment : Fragment() {

     private val delay= 300L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        Timer().schedule(delay) {
            //val action=
            //findNavController().navigate()
        }

        return inflater.inflate(R.layout.fragmet_splash_screen, container, false)
    }

}
