package com.movie.customview.adapter

import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.movie.model.data.MovieMainResponse


//@BindingAdapter("url")
//fun setImageUrl(view: ImageView, profileUrl: String?) {
//
//    if (!TextUtils.isEmpty(profileUrl)) {
//        Glide.with(view.context)
//            .load(profileUrl)
//            .into(view)
//    }
//}

@BindingAdapter("app:recycler_adapter")
fun setRecyclerAdapter(view: RecyclerView, adapter: CustomListAdapter?) {
    adapter?.let {
        view.adapter = it
    }
}

@BindingAdapter("app:recycler_item")
fun setRecyclerItems(view: RecyclerView, movieList: List<MovieMainResponse.Movie>) {
    movieList.let {
        val adapter = view.adapter as CustomListAdapter
        adapter.setItem(movieList)
        adapter.notifyDataSetChanged()
    }
}

@BindingAdapter("app:adapter")
fun setPagerAdapter(view: ViewPager2, upcomingPagerAdapter: UpcomingPagerAdapter) {
    upcomingPagerAdapter.let {
        view.adapter = it
        view.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }
}

@BindingAdapter("app:item")
fun setBindItems(view: ViewPager2, movieList: List<MovieMainResponse.Movie>) {
    movieList.let {
        val adapter = view.adapter as UpcomingPagerAdapter
        adapter.setItem(movieList)
    }
}