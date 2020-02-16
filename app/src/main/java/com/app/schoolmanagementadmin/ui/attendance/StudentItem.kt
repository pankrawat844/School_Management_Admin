package com.app.schoolmanagementadmin.ui.attendance

import com.app.schoolmanagementadmin.R
import com.app.schoolmanagementadmin.databinding.ItemAttendenceBinding
import com.app.schoolmanagementadmin.network.response.StudentList
import com.xwray.groupie.databinding.BindableItem

class StudentItem(val item:StudentList.Response):BindableItem<ItemAttendenceBinding>() {
    override fun getLayout(): Int =R.layout.item_attendence

    override fun bind(viewBinding: ItemAttendenceBinding, position: Int) {
            viewBinding.data=item
    }
}