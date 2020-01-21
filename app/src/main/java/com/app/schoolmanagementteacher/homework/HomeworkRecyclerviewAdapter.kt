package com.app.schoolmanagementteacher.homework

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.app.schoolmanagementteacher.R
import com.app.schoolmanagementteacher.response.HomeworkList

class HomeworkRecyclerviewAdapter(val list:List<HomeworkList.Response>):RecyclerView.Adapter<HomeworkRecyclerviewAdapter.Viewholder>() {

    class Viewholder(itemView: View):RecyclerView.ViewHolder(itemView)
    {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        var v:View
        if(viewType==0)
        v=LayoutInflater.from(parent.context).inflate(R.layout.homework_listitem_img,parent,false)
        else
            v=LayoutInflater.from(parent.context).inflate(R.layout.homework_listitem_img,parent,false)

        return Viewholder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
}