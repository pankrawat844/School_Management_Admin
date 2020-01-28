package com.app.schoolmanagementteacher.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


class DataBinder {
    companion object {
        @BindingAdapter("src1")
        @JvmStatic
        fun setImageUrl(imageView: ImageView, url: String?) {
            Glide.with(imageView.context).load(url).into(imageView)
        }

    }


}