package com.movie.customview.adapter

import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.movie.R
import com.movie.common.constants.PAGER_COUNT
import com.movie.customview.view.CustomIndicator
import com.movie.model.data.MovieMainResponse
import com.movie.model.view.PagerViewModel

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

@BindingAdapter("pageAdapter", "indicator")
fun setPagerAdapter(view: ViewPager, upcomingPagerAdapter: UpcomingPagerAdapter?, indicator: CustomIndicator) {
    upcomingPagerAdapter?.let {
        view.adapter = upcomingPagerAdapter
        view.offscreenPageLimit = PAGER_COUNT
        view.currentItem = 0
        view.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                indicator.selectDot(position)
            }
        })
    }
}

@BindingAdapter("pageItem")
fun setPageItem(view: ViewPager, pagerViewModelList: MutableList<PagerViewModel>?) {
    pagerViewModelList?.let {
        val adapter = view.adapter as UpcomingPagerAdapter
        adapter.setItem(pagerViewModelList)
        adapter.notifyDataSetChanged()
    }
}

@BindingAdapter("imgUrl")
fun setImageUrl(view: ImageView, url: String?) {
    if (!TextUtils.isEmpty(url)) {
        Glide.with(view.context)
            .load(url)
            .override(
                view.context.resources.displayMetrics.widthPixels,
                view.context.resources.displayMetrics.widthPixels / 3
            )
            .error(R.drawable.film_poster_placeholder)
            .placeholder(R.drawable.film_poster_placeholder)
            .into(view)
    }
}