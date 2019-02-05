package com.movie.customview.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import com.movie.R
import com.movie.common.constants.MovieConstant
import com.movie.model.data.VideoResponse

class VideoListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    //layout
    private val wvContents: WebView = itemView.findViewById(R.id.wv_contents)


    @SuppressLint("SetJavaScriptEnabled")
    internal fun setData(videoList: List<VideoResponse.Videos>, position: Int) {
        wvContents.settings.javaScriptEnabled = true
        wvContents.loadUrl(MovieConstant.YOUTUBE_URL + videoList[position].key)
    }
}

class VideoListAdapter(private val mContext: Context, private var videoList: List<VideoResponse.Videos>) : RecyclerView.Adapter<VideoListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoListHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.view_video_list_item, parent, false)
        return VideoListHolder(view)
    }

    override fun onBindViewHolder(holder: VideoListHolder, position: Int) {
        holder.setData(videoList, position)
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

}

