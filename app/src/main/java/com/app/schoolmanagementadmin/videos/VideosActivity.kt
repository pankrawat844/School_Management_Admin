package com.app.schoolmanagementadmin.videos

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.schoolmanagementadmin.R
import com.app.schoolmanagementadmin.network.response.YoutubeVideo
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_videos.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

/**
 * A simple [Fragment] subclass.
 */
class VideosActivity : AppCompatActivity(), KodeinAware {
    override val kodein: Kodein by kodein()
    val factory: VideosViewModelFactory by instance()
    lateinit var viewModel: VideosViewModel
    var list = ArrayList<YoutubeVideo.Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_videos)
        viewModel = ViewModelProviders.of(this, factory).get(VideosViewModel::class.java)
        Glide.with(this).asGif().load(R.drawable.cricket_ball).diskCacheStrategy(
            DiskCacheStrategy.RESOURCE
        ).into(ball_rotate)
        ball_rotate.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getVideolist.await().observe(this@VideosActivity, Observer {
                //               Log.e("response", it.toString())
//              if(list.size>0) {
                ball_rotate.visibility = View.GONE

                list = it.items as ArrayList<YoutubeVideo.Item>
                initReyclervew(it.items.changeList())
//              }
            })
        }
    }


    private fun initReyclervew(items: List<YoutubeList>) {
        var groupadapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }
        youtube_list.layoutManager = LinearLayoutManager(this)
        youtube_list.adapter = groupadapter
        groupadapter.setOnItemClickListener { item, view ->
            startActivity(Intent(this, YoutubeDetailActivity::class.java).also {
                val itemm = item as YoutubeList
                val args = Bundle()
//                    args.putSerializable("data",itemm as Serializable)
//             it.putExtra("data",args)
                viewModel.setVideoId(itemm.data.id.videoId)

                it.putExtra("videoId", itemm.data.id.videoId)
                it.putExtra("video", list[groupadapter.getAdapterPosition(item)])
            })

        }
    }

    private fun List<YoutubeVideo.Item>.changeList(): List<YoutubeList> {
        return this.map {
            YoutubeList(it)
        }
    }
}
