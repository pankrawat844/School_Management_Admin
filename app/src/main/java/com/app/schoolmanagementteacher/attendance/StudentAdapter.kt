package com.app.schoolmanagementteacher.attendance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.schoolmanagementteacher.R
import com.app.schoolmanagementteacher.response.StudentList
import kotlinx.android.synthetic.main.item_attendence.view.*

class StudentAdapter(val list:List<StudentList.Response>):RecyclerView.Adapter<StudentAdapter.Viewholder>() {

class Viewholder(itemView:View):RecyclerView.ViewHolder(itemView)
{
    val name:TextView=itemView.findViewById(R.id.name)
    val present:RadioButton=itemView.findViewById(R.id.present)
    val absent:RadioButton=itemView.findViewById(R.id.absent)

}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_attendence,parent,false)
        return Viewholder(view)
        }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.name.text=list[position].name

    }
}