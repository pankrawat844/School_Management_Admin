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
    var optionSelected: OnOptionSelected? = null

    class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val radioGroup = itemView.radio_grp
        var present: RadioButton = itemView.present
        val absent: RadioButton = itemView.absent

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_attendence,parent,false)
        return Viewholder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.name.text = list[position].name

        list[position].attendence.let {
            if(it=="p")
                holder.present.isChecked=true
            if(it=="a")
                holder.absent.isChecked=true
        }
        holder.radioGroup.tag = position
        holder.absent.tag = position
        holder.present.tag = position
        holder.radioGroup.setOnCheckedChangeListener { group, checkedId ->

            if (checkedId == R.id.present) {
//                list[position].attendence="p"
                holder.radioGroup.getTag(position)
                optionSelected?.onOptionSelected(position, "p")
            }
            if (checkedId == R.id.absent) {
//                list[position].attendence="a"
                optionSelected?.onOptionSelected(position, "a")

            }

        }


    }
}