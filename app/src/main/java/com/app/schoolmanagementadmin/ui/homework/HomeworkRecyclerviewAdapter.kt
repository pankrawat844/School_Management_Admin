package com.app.schoolmanagementadmin.ui.homework

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.schoolmanagementadmin.R
import com.app.schoolmanagementadmin.network.response.HomeworkList
import com.app.schoolmanagementadmin.utils.Constants
import com.squareup.picasso.Picasso

class HomeworkRecyclerviewAdapter(val list:List<HomeworkList.Response>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    companion object
    {
        private const val TYPE_TXT = 0
        private const val TYPE_IMG = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when(viewType){
                TYPE_TXT->{
                    val view=LayoutInflater.from(parent.context).inflate(R.layout.homework_listitem_txt,parent,false)
                     TxtViewholder(view)
                }
                TYPE_IMG->{
                    val view=LayoutInflater.from(parent.context).inflate(R.layout.homework_listitem_img,parent,false)
                     ImageViewholder(view)
                }
                else -> throw IllegalArgumentException("Invalid view type")
            }
    }

    override fun getItemCount(): Int {

    return list.size
    }

    override fun getItemViewType(position: Int): Int {
        val viewtype=list[position]
       return when(viewtype.type) {
            0 -> TYPE_TXT
            1-> TYPE_IMG
           else-> throw IllegalArgumentException("Invalid type of data " + position)
       }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            TYPE_TXT->{
                val txtViewholder=holder as TxtViewholder
                txtViewholder.txtview.text=list[position].homeworkTxt
            }
            TYPE_IMG->{
                val imgViewholder=holder as ImageViewholder
                Picasso.get().load(Constants.base_url+list[position].homeworkImg).error(R.drawable.gallery).into(imgViewholder.imageview)
            }
        }
    }

    class ImageViewholder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
            val imageview:ImageView=itemView.findViewById(R.id.img)

    }

    class TxtViewholder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
        val txtview:TextView=itemView.findViewById(R.id.txt_view)

    }
}
