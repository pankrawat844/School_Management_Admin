package com.app.schoolmanagementteacher.upcomingtest

import com.app.schoolmanagementteacher.R
import com.app.schoolmanagementteacher.databinding.ItemNoticeBinding
import com.app.schoolmanagementteacher.databinding.ItemTestBinding
import com.app.schoolmanagementteacher.response.NoticeList
import com.app.schoolmanagementteacher.response.UpcomingTestList
import com.xwray.groupie.databinding.BindableItem

class UpcoingTestItem(val item:UpcomingTestList.Response) :BindableItem<ItemTestBinding>()
{
    override fun getLayout(): Int {
        return R.layout.item_test
    }

    override fun bind(viewBinding: ItemTestBinding, position: Int) {
         viewBinding.data=item
    }

}