package com.movie.customview.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.movie.R
import com.movie.util.YOUTUBE_URL
import com.movie.databinding.ViewVideoListItemBinding
import com.movie.model.data.VideoResponse

class VideoListHolder(binding: ViewVideoListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    private val wvContents: WebView = itemView.findViewById(R.id.wv_contents)

    @SuppressLint("SetJavaScriptEnabled")
    internal fun setData(videoList: List<VideoResponse.Videos>, position: Int) {
        wvContents.settings.javaScriptEnabled = true
        wvContents.loadUrl(YOUTUBE_URL + videoList[position].key)
    }
}

class VideoListAdapter : RecyclerView.Adapter<VideoListHolder>() {

    private var videoList: List<VideoResponse.Videos> = mutableListOf()

    fun setItem(_videoList: List<VideoResponse.Videos>?) {
        videoList = if (_videoList.isNullOrEmpty()) {
            mutableListOf()
        } else {
            _videoList
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoListHolder {
        val binding: ViewVideoListItemBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.view_video_list_item, parent, false)
        return VideoListHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoListHolder, position: Int) {
        holder.setData(videoList, position)
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

}

