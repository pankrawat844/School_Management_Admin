package com.app.schoolmanagementteacher.notice

import com.app.schoolmanagementteacher.R
import com.app.schoolmanagementteacher.databinding.ItemNoticeBinding
import com.app.schoolmanagementteacher.response.NoticeList
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