package com.haytham.coder.graduationproject.presentation.fragments

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.haytham.coder.graduationproject.R
import com.haytham.coder.graduationproject.databinding.FragmentProfileBinding
import com.haytham.coder.graduationproject.presentation.activity.MainActivity


class ProfileFragment : Fragment() {
    private lateinit var dataBinding: FragmentProfileBinding
    private val args:ProfileFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        (activity as MainActivity).hideBottomBar()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding= FragmentProfileBinding.inflate(inflater).apply {
            studentModel= args.studentModel
            studentImage.transitionName= root.resources.getString(R.string.transition_student_image) + args.studentModel.studentId
            studentName.transitionName= root.resources.getString(R.string.transition_student_name) + args.studentModel.studentId

            backArrow.setOnClickListener {
                findNavController().popBackStack()
            }

        }

        return dataBinding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).showBottomBar()
    }
}
