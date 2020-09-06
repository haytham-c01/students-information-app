package com.haytham.coder.graduationproject.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.haytham.coder.graduationproject.R
import com.haytham.coder.graduationproject.databinding.ItemStudentBinding
import com.haytham.coder.graduationproject.domain.model.StudentModel


class StudentsAdapter(private val canWrite:Boolean) :
    ListAdapter<StudentModel, StudentsAdapter.ViewHolder>(DIFF_UTIL_CALLBACK) {


    companion object {
        private const val TAG = "StudentsQuickAdapter"
        private val DIFF_UTIL_CALLBACK = object : DiffUtil.ItemCallback<StudentModel>() {
            override fun areItemsTheSame(oldItem: StudentModel, newItem: StudentModel) =
                oldItem.studentId == newItem.studentId

            override fun areContentsTheSame(oldItem: StudentModel, newItem: StudentModel) =
                oldItem == newItem

        }
    }


    var phoneIconPressed: ((phoneNumber: String) -> Unit)? = null
    var copyIconPressed: ((phoneNumber: String) -> Unit)? = null
    var deleteIconPressed: ((studentId: String) -> Unit)? = null
    var editIconPressed: (() -> Unit)? = null
    var studentImagePressed: ((studentBinding: ItemStudentBinding, studentModel: StudentModel) -> Unit)? =
        null


    class ViewHolder(val binding: ItemStudentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(studentModel: StudentModel) {
            binding.apply {
                this.studentModel = studentModel
                executePendingBindings()
            }

        }
        companion object {
            fun from(parent: ViewGroup) = ViewHolder(
                ItemStudentBinding
                    .inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
            )
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            deleteIcon.isVisible= canWrite
            studentImage.transitionName =
                root.resources.getString(R.string.transition_student_image) + item.studentId
            studentName.transitionName =
                root.resources.getString(R.string.transition_student_name) + item.studentId

            studentImage.setOnClickListener {
                studentImagePressed?.invoke(this, item)
            }
            // set model
            phoneIcon.setOnClickListener {
                phoneIconPressed?.invoke(item.contact.phone)
            }

            copyIcon.setOnClickListener {
                copyIconPressed?.invoke(item.contact.phone)
            }

            deleteIcon.setOnClickListener {
                deleteIconPressed?.invoke(item.studentId)
            }

            editIcon.setOnClickListener {
                editIconPressed?.invoke()
            }

        }
        holder.bind(item)
    }


}