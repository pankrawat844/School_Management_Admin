package com.app.schoolmanagementteacher.videos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.schoolmanagementteacher.R
import com.app.schoolmanagementteacher.response.YoutubeVideo
import com.app.schoolmanagementteacher.response.YoutubeVideoRelated
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_youtube_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class YoutubeDetailActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    val factory: YoutubeDetailViewModelFactory by instance()
    lateinit var viewModel: YoutubeDetailViewModel
    lateinit var list: List<YoutubeVideoRelated.Item>

    lateinit var video_id: String
    lateinit var video: YoutubeVideo.Item
    lateinit var video1: YoutubeVideoRelated.Item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube_detail)
        viewModel = ViewModelProviders.of(this, factory).get(YoutubeDetailViewModel::class.java)
        video_id = intent.getStringExtra("videoId")!!
        if (intent.extras?.getSerializable("video") is YoutubeVideo.Item) {
            video = intent.extras?.getSerializable("video") as YoutubeVideo.Item
        } else {
            video1 = intent.extras?.getSerializable("video") as YoutubeVideoRelated.Item

        }

        viewModel.setVideoId(video_id)
        val youtubeFragment: YouTubePlayerSupportFragment =
            supportFragmentManager.findFragmentById(R.id.youtubeVideo) as YouTubePlayerSupportFragment
        youtubeFragment.initialize(
            getString(R.string.youtube_key),
            object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(
                    provider: YouTubePlayer.Provider,
                    youTubePlayer: YouTubePlayer,
                    b: Boolean
                ) {
                    youTubePlayer.loadVideo(video_id)
                    youTubePlayer.setShowFullscreenButton(true)
                }

                override fun onInitializationFailure(
                    provider: YouTubePlayer.Provider,
                    youTubeInitializationResult: YouTubeInitializationResult
                ) {
                }
            })
        if (intent.extras?.getSerializable("video") is YoutubeVideo.Item) {
            video_desc.text = video.snippet.description
            video_ttl.text = video.snippet.title
        } else {
            video_desc.text = video1.snippet.description
            video_ttl.text = video1.snippet.title
        }

//        viewModel.getRelatedVideolist(video_id)

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.response.await().observe(this@YoutubeDetailActivity, Observer {
                list = it.items
                initReyclervew(it.items.changeList())

            })
        }
    }

    private fun initReyclervew(items: List<YoutubeDetailList>) {
        var groupadapter = GroupAdapter<ViewHolder>().apply {
            Log.e("responseeeeeee", items.toString())
            addAll(items)
        }
        relatedVideoRecyclerView.layoutManager = LinearLayoutManager(this)
        relatedVideoRecyclerView.adapter = groupadapter
        groupadapter.setOnItemClickListener { item, view ->
            startActivity(Intent(this, YoutubeDetailActivity::class.java).also {
                val itemm = item as YoutubeDetailList
                val args = Bundle()
//                    args.putSerializable("data",itemm as Serializable)
//             it.putExtra("data",args)
                it.putExtra("videoId", itemm.data.id.videoId)
                it.putExtra("video", list[groupadapter.getAdapterPosition(item)])
            })
            finish()

        }
    }

    private fun List<YoutubeVideoRelated.Item>.changeList(): List<YoutubeDetailList> {
        return this.map {
            YoutubeDetailList(it)
        }
    }
}
