package com.movie.customview.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movie.R
import com.movie.activity.DetailMovieActivity
import com.movie.common.constants.IMAGE_URL
import com.movie.common.constants.MOVIE_ID
import com.movie.databinding.ViewPagerLayoutBinding
import com.movie.model.data.MovieMainResponse


class UpcomingPagerHolder(private val binding: ViewPagerLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    private val context: Context = binding.root.context

    internal fun setData(movieList: List<MovieMainResponse.Movie>, position: Int) {
        binding.flPagerItem.setOnClickListener {
            val intent = Intent(context, DetailMovieActivity::class.java)
            intent.putExtra(MOVIE_ID, movieList[position].id)
            ActivityCompat.startActivity(context, intent, null)
        }

        val url = IMAGE_URL + movieList[position].backdropPath
        Glide.with(context)
            .load(url)
            .override(context.resources.displayMetrics.widthPixels, context.resources.displayMetrics.widthPixels / 3)
            .error(R.drawable.film_poster_placeholder)
            .placeholder(R.drawable.film_poster_placeholder)
            .into(binding.ivMovieItem)

        binding.tvMovieTitle.text = movieList[position].title
    }
}

class UpcomingPagerAdapter : RecyclerView.Adapter<UpcomingPagerHolder>() {

    private lateinit var movieList: List<MovieMainResponse.Movie>

    fun setItem(_movieList: List<MovieMainResponse.Movie>) {
        movieList = _movieList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingPagerHolder {
        val binding: ViewPagerLayoutBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.view_pager_layout, parent, false)
        return UpcomingPagerHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: UpcomingPagerHolder, position: Int) {
        holder.setData(movieList, position)
    }
}