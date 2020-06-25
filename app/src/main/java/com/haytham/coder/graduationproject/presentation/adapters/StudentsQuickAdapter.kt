package com.haytham.coder.graduationproject.presentation.adapters

import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.haytham.coder.graduationproject.R
import com.haytham.coder.graduationproject.databinding.ItemStudentBinding


class StudentsQuickAdapter :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_student) {
    override fun convert(holder: BaseViewHolder, item: String) {
        val binding: ItemStudentBinding? = DataBindingUtil.bind(holder.itemView)


        binding?.apply {
            root.setOnClickListener {
                it.findNavController().navigate(R.id.action_global_profileFragment)
            }
            // set model
            executePendingBindings()
        }
    }


}