package com.app.schoolmanagementadmin.ui.notice

import com.app.schoolmanagementadmin.R
import com.app.schoolmanagementadmin.databinding.ItemNoticeBinding
import com.app.schoolmanagementadmin.network.response.NoticeList
import com.xwray.groupie.databinding.BindableItem

class NoticeItem(val item:NoticeList.Response) :BindableItem<ItemNoticeBinding>()
{
    override fun getLayout(): Int {
        return R.layout.item_notice
    }

    override fun bind(viewBinding: ItemNoticeBinding, position: Int) {
         viewBinding.data=item
    }

}