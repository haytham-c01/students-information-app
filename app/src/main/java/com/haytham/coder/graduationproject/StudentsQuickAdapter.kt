package com.haytham.coder.graduationproject

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.haytham.coder.graduationproject.databinding.ItemStudentBinding


class StudentsQuickAdapter :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_student) {
    override fun convert(holder: BaseViewHolder, item: String) {
        val binding: ItemStudentBinding? = DataBindingUtil.getBinding(holder.itemView)

        binding?.apply {
            // set model
            executePendingBindings()
        }
    }


}