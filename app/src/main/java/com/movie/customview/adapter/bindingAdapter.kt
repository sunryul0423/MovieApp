package com.movie.customview.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.movie.R
import com.movie.common.constants.PAGER_COUNT
import com.movie.common.constants.SEARCH_SPAN_COUNT
import com.movie.customview.view.CustomIndicator
import com.movie.model.data.MovieMainResponse
import com.movie.model.data.RecyclerViewSpacing
import com.movie.model.view.PagerViewModel
import com.movie.model.view.SearchViewModel

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
        adapter.setItem(it, isVote)
        adapter.notifyDataSetChanged()
    }
}

@BindingAdapter("pageAdapter", "indicator")
fun setPagerAdapter(view: ViewPager, upcomingPagerAdapter: UpcomingPagerAdapter?, indicator: CustomIndicator) {
    upcomingPagerAdapter?.let {
        with(view) {
            adapter = it
            offscreenPageLimit = PAGER_COUNT
            currentItem = 0
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
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
}

@BindingAdapter("pageItem")
fun setPageItem(view: ViewPager, pagerViewModelList: MutableList<PagerViewModel>?) {
    pagerViewModelList?.let {
        (view.adapter as UpcomingPagerAdapter).apply {
            this.setItem(it)
            this.notifyDataSetChanged()
        }

    }
}

@BindingAdapter("imgUrl")
fun setImageUrl(view: ImageView, url: String?) {
    url?.let {
        Glide.with(view.context)
            .load(it)
            .override(
                view.context.resources.displayMetrics.widthPixels,
                view.context.resources.displayMetrics.widthPixels / 3
            )
            .error(R.drawable.film_poster_placeholder)
            .placeholder(R.drawable.film_poster_placeholder)
            .into(view)
    }
}

@BindingAdapter("searchAdapter")
fun setSearchAdapter(view: RecyclerView, adapter: SimilarGridAdapter?) {
    adapter?.let {
        val spacing: Int = view.context.resources.getDimensionPixelSize(R.dimen.detail_search_grid_margin)
        val recyclerViewDecoration = RecyclerViewDecoration(
            true,
            SEARCH_SPAN_COUNT,
            RecyclerViewSpacing(spacing, spacing, spacing, spacing)
        )

        view.setHasFixedSize(true)
        view.addItemDecoration(recyclerViewDecoration)
        view.layoutManager = GridLayoutManager(view.context, SEARCH_SPAN_COUNT)
        view.adapter = it
    }
}

@BindingAdapter("searchItem", "searchAdd")
fun setSearchItem(view: RecyclerView, searchList: MutableList<MovieMainResponse.Movie>?, isAdd: Boolean) {
    searchList?.let {
        val adapter = view.adapter as SimilarGridAdapter
        adapter.addList(it, isAdd)
        adapter.notifyDataSetChanged()
    }
}


@BindingAdapter("searchModel")
fun setSearchModel(view: RecyclerView, searchViewModel: SearchViewModel?) {
    searchViewModel?.let {
        view.setOnScrollChangeListener { _, _, _, _, _ ->
            if (!view.canScrollVertically(1)) { //최하단 스크롤
                it.reqeustApi(it.correntPage, true)
            }
        }
    }
}