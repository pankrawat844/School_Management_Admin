package com.app.schoolmanagementadmin.videos

import com.app.schoolmanagementadmin.R
import com.app.schoolmanagementadmin.databinding.VideoListBinding
import com.app.schoolmanagementadmin.network.response.YoutubeVideo
import com.xwray.groupie.databinding.BindableItem
import java.io.Serializable

class YoutubeList(val data: YoutubeVideo.Item) : BindableItem<VideoListBinding>(), Serializable {

    override fun getLayout(): Int {
        return R.layout.video_list
    }

    override fun bind(viewBinding: VideoListBinding, position: Int) {
        viewBinding.viewmodel = data
    }


//    @BindingAdapter("app:profileImage")
//    fun setImageUrl(imageView: ImageView, url: String) {
//        val context = imageView.getContext()
//        Glide.with(context).load(url).into(imageView)
//    }


}