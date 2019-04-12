package com.movie.customview.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.movie.model.data.MovieMainResponse
import com.movie.model.view.MainViewModel


//@BindingAdapter("url")
//fun setImageUrl(view: ImageView, profileUrl: String?) {
//
//    if (!TextUtils.isEmpty(profileUrl)) {
//        Glide.with(view.context)
//            .load(profileUrl)
//            .into(view)
//    }
//}

@BindingAdapter("recyclerAdapter")
fun setRecyclerAdapter(view: RecyclerView, adapter: CustomListAdapter?) {
    adapter?.let {
        view.adapter = it
        view.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(view.context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        view.layoutManager = layoutManager
    }
}

@BindingAdapter("recyclerItem", "isVote")
fun setRecyclerItem(view: RecyclerView, movieList: List<MovieMainResponse.Movie>?, isVote: Boolean) {
    movieList?.let {
        val adapter = view.adapter as CustomListAdapter
        adapter.setItem(movieList, isVote)
        adapter.notifyDataSetChanged()
    }
}

@BindingAdapter("pageAdapter")
fun setPagerAdapter(view: ViewPager2, upcomingPagerAdapter: UpcomingPagerAdapter?) {
    upcomingPagerAdapter?.let {
        view.adapter = it
        view.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        view.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {

            }
        })
    }
}

@BindingAdapter("pageItem")
fun setPageItem(view: ViewPager2, movieList: List<MovieMainResponse.Movie>?) {
    movieList?.let {
        val adapter = view.adapter as UpcomingPagerAdapter
        adapter.setItem(movieList)
        adapter.notifyDataSetChanged()
    }
}

@BindingAdapter("isRefresh")
fun onRefresh(view: SwipeRefreshLayout, isRefresh: Boolean) {
    view.isRefreshing = isRefresh
}