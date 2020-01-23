package com.app.schoolmanagementteacher.attendance

import com.app.schoolmanagementteacher.R
import com.app.schoolmanagementteacher.databinding.ItemAttendenceBinding
import com.app.schoolmanagementteacher.response.StudentList
import com.xwray.groupie.databinding.BindableItem

class StudentItem(val item:StudentList.Response):BindableItem<ItemAttendenceBinding>() {
    override fun getLayout(): Int =R.layout.item_attendence

    override fun bind(viewBinding: ItemAttendenceBinding, position: Int) {
            viewBinding.data=item
    }
}