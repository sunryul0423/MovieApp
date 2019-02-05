package com.movie.customview.adapter

import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.movie.R
import com.movie.activity.DetailMovieActivity
import com.movie.common.constants.MovieConstant
import com.movie.model.data.MovieMainResponse

class MovieListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    //layout
    private val llListItem: LinearLayout = itemView.findViewById(R.id.ll_list_item)
    private val ivMoviePoster: ImageView = itemView.findViewById(R.id.iv_movie_poster)
    private val tvMovieTitle: TextView = itemView.findViewById(R.id.tv_movie_title)
    private val tvVoteAverage: TextView = itemView.findViewById(R.id.tv_vote_average)

    internal fun setData(mContext: Context, isVote: Boolean, movieList: List<MovieMainResponse.Movie>, position: Int) {
        llListItem.setOnClickListener {
            val intent = Intent(mContext, DetailMovieActivity::class.java)
            intent.putExtra(MovieConstant.MOVIE_ID, movieList[position].id)
            ActivityCompat.startActivity(mContext, intent, null)
        }
        val url = MovieConstant.IMAGE_URL + movieList[position].posterPath
        Glide.with(mContext)
                .load(url)
                .override(400, 600)
                .centerCrop()
                .error(R.drawable.film_poster_placeholder)
                .placeholder(R.drawable.film_poster_placeholder)
                .into(ivMoviePoster)

        tvMovieTitle.text = movieList[position].title
        if (isVote) {
            tvVoteAverage.visibility = View.VISIBLE
            tvVoteAverage.text = "${movieList[position].voteAverage}"
        } else {
            tvVoteAverage.visibility = View.GONE
        }

    }
}

class CustomListAdapter(private val mContext: Context, private val isVote: Boolean, private var movieList: List<MovieMainResponse.Movie>) : RecyclerView.Adapter<MovieListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.view_movie_list_item, parent, false)
        return MovieListHolder(view)
    }

    override fun onBindViewHolder(holder: MovieListHolder, position: Int) {
        holder.setData(mContext, isVote, movieList, position)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

}

