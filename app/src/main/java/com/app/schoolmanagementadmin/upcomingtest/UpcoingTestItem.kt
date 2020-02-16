package com.app.schoolmanagementadmin.upcomingtest

import com.app.schoolmanagementadmin.R
import com.app.schoolmanagementadmin.databinding.ItemTestBinding
import com.app.schoolmanagementadmin.network.response.UpcomingTestList
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