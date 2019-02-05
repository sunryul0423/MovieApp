package com.movie.customview.adapter

import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.movie.R
import com.movie.activity.DetailMovieActivity
import com.movie.common.constants.MovieConstant
import com.movie.model.data.MovieMainResponse

class UpcomingPagerAdapter(private val mContext: Context, private var movieList: List<MovieMainResponse.Movie>) : PagerAdapter() {

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun getCount(): Int {
        return movieList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(mContext).inflate(R.layout.view_pager_layout, container, false)
        val flPagerItem: FrameLayout = view.findViewById(R.id.fl_pager_item)
        flPagerItem.setOnClickListener {
            val intent = Intent(mContext, DetailMovieActivity::class.java)
            intent.putExtra(MovieConstant.MOVIE_ID, movieList[position].id)
            ActivityCompat.startActivity(mContext, intent, null)
        }

        val tvMovieTitle: TextView = view.findViewById(R.id.tv_movie_title)
        val ivMovieItem: ImageView = view.findViewById(R.id.iv_movie_item)
        val url = MovieConstant.IMAGE_URL + movieList[position].backdropPath
        Glide.with(mContext)
                .load(url)
                .override(mContext.resources.displayMetrics.widthPixels, mContext.resources.displayMetrics.widthPixels / 3)
                .error(R.drawable.film_poster_placeholder)
                .placeholder(R.drawable.film_poster_placeholder)
                .into(ivMovieItem)

        tvMovieTitle.text = movieList[position].title

        container.addView(view, position)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}