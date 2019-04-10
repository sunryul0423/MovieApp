package com.movie.common.utils

import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.movie.customview.adapter.CustomListAdapter
import com.movie.customview.adapter.UpcomingPagerAdapter
import com.movie.model.data.MovieMainResponse

@BindingAdapter("url")
fun setImageUrl(view: ImageView, profileUrl: String?) {

    if (!TextUtils.isEmpty(profileUrl)) {
        Glide.with(view.context)
            .load(profileUrl)
            .into(view)
    }
}

@BindingAdapter("recycler_adapter")
fun setBindAdapter(view: RecyclerView, adapter: CustomListAdapter?) {
    adapter?.let {
        view.adapter = it
    }
}

//@BindingAdapter("recycler_items")
//fun setBindItems(view: RecyclerView, items: List<User>?) {
//    items?.let {
//
//        val adapter = view.adapter as MainAdapter
//        adapter.setItems(items)
//        adapter.notifyDataSetChanged()
//    }
//}

@BindingAdapter("pager_adapter")
fun setPagerAdapter(pager: ViewPager2, adapter: UpcomingPagerAdapter?) {
    adapter.let {
        pager.adapter = it
        pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }
}

@BindingAdapter("pager_item")
fun setBindItems(pager: ViewPager, movieList: List<MovieMainResponse.Movie>) {
    movieList.let {
        val adapter = pager.adapter as UpcomingPagerAdapter
        adapter.setItem(movieList)
    }
}