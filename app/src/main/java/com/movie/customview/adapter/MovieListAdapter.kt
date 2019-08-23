package com.movie.customview.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movie.R
import com.movie.activity.DetailMovieActivity
import com.movie.util.IMAGE_URL
import com.movie.util.MOVIE_ID
import com.movie.databinding.ViewMovieListItemBinding
import com.movie.model.data.MovieMainResponse

class MovieListHolder(private val binding: ViewMovieListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    private val context: Context = binding.root.context

    internal fun setData(isVote: Boolean, movieList: List<MovieMainResponse.Movie>, position: Int) {
        binding.llListItem.setOnClickListener {
            val intent = Intent(context, DetailMovieActivity::class.java)
            intent.putExtra(MOVIE_ID, movieList[position].id)
            ActivityCompat.startActivity(context, intent, null)
        }

        val url = IMAGE_URL + movieList[position].posterPath
        Glide.with(context)
            .load(url)
            .override(400, 600)
            .centerCrop()
            .error(R.drawable.film_poster_placeholder)
            .placeholder(R.drawable.film_poster_placeholder)
            .into(binding.ivMoviePoster)

        binding.tvMovieTitle.text = movieList[position].title
        if (isVote) {
            binding.tvVoteAverage.visibility = View.VISIBLE
            binding.tvVoteAverage.text = "${movieList[position].voteAverage}"
        } else {
            binding.tvVoteAverage.visibility = View.GONE
        }
    }
}

class CustomListAdapter : RecyclerView.Adapter<MovieListHolder>() {

    private var movieList: List<MovieMainResponse.Movie> = mutableListOf()
    private var isVote: Boolean = false

    fun setItem(_movieList: List<MovieMainResponse.Movie>?, _isVote: Boolean) {
        movieList = if (_movieList.isNullOrEmpty()) {
            mutableListOf()
        } else {
            _movieList
        }
        isVote = _isVote
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListHolder {
        val binding: ViewMovieListItemBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.view_movie_list_item, parent, false)
        return MovieListHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieListHolder, position: Int) {
        holder.setData(isVote, movieList, position)
    }
}