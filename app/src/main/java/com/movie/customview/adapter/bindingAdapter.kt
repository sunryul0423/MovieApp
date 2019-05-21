package com.movie.customview.adapter

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.movie.R
import com.movie.common.constants.CREDIT_COUNT
import com.movie.common.constants.PAGER_COUNT
import com.movie.common.constants.SEARCH_SPAN_COUNT
import com.movie.customview.view.CustomIndicator
import com.movie.model.data.*
import com.movie.model.view.CreaditInfoViewModel
import com.movie.model.view.PagerViewModel
import com.movie.model.view.SearchViewModel
import jp.wasabeef.glide.transformations.CropCircleTransformation

@BindingAdapter("recyclerAdapter")
fun setRecyclerAdapter(view: RecyclerView, adapter: CustomListAdapter?) {
    adapter?.let {
        view.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(view.context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        view.layoutManager = layoutManager
        view.adapter = it
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

@BindingAdapter("creditImgUrl")
fun setcreditImgUrl(view: ImageView, url: String?) {
    url?.let {
        val bitmapPool = object : BitmapPool {
            override fun setSizeMultiplier(p0: Float) {
            }

            override fun trimMemory(p0: Int) {
            }

            override fun get(p0: Int, p1: Int, p2: Bitmap.Config?): Bitmap? {
                return null
            }

            override fun clearMemory() {
            }

            override fun getDirty(p0: Int, p1: Int, p2: Bitmap.Config?): Bitmap? {
                return null
            }

            override fun getMaxSize(): Int {
                return 0
            }

            override fun put(p0: Bitmap?): Boolean {
                return false
            }
        }
        Glide.with(view.context)
            .load(url)
            .override(600, 600)
            .centerCrop()
            .bitmapTransform(CropCircleTransformation(bitmapPool))
            .placeholder(R.drawable.profile)
            .error(R.drawable.profile)
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
fun setSearchItem(view: RecyclerView, searchList: List<MovieMainResponse.Movie>?, isAdd: Boolean) {
    searchList?.let {
        val adapter = view.adapter as SimilarGridAdapter
        adapter.addList(it, isAdd)
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

@BindingAdapter("videoListAdapter")
fun setVideoListAdapter(view: RecyclerView, adapter: VideoListAdapter?) {
    adapter?.let {
        view.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(view.context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        view.layoutManager = layoutManager
        view.adapter = it
    }
}

@BindingAdapter("videoListItem")
fun setVideoListItem(view: RecyclerView, videoList: List<VideoResponse.Videos>?) {
    videoList?.let {
        val spacing: Int = view.context.resources.getDimensionPixelSize(R.dimen.detail_overview_credit_divider)
        val recyclerViewDecoration =
            RecyclerViewDecoration(false, videoList.size, RecyclerViewSpacing(spacing, spacing, spacing, spacing))
        view.addItemDecoration(recyclerViewDecoration)

        val adapter = view.adapter as VideoListAdapter
        adapter.setItem(it)
        adapter.notifyDataSetChanged()
    }
}

@BindingAdapter("creditListAdapter")
fun setCreditListAdapter(view: RecyclerView, adapter: CreditListAdapter?) {
    adapter?.let {
        view.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(view.context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        view.layoutManager = layoutManager
        view.adapter = it
    }
}

@BindingAdapter("creditListItem")
fun setCreditListItem(view: RecyclerView, creditResponse: CreditResponse?) {
    creditResponse?.let {

        val creaditInfoList: MutableList<CreaditInfoViewModel> = mutableListOf()
        var castList = it.cast
        val crewList = it.crew

        for (i in crewList.indices) {
            if ("Director" == crewList[i].job) {
                val creaditInfoModel =
                    CreaditInfoViewModel().apply {
                        setImageUrl(crewList[i].profilePath)
                        setName(crewList[i].name)
                        setPart(crewList[i].job)
                    }
                creaditInfoList.add(creaditInfoModel)
                break
            }
        }

        if (CREDIT_COUNT < castList.size) {
            castList = castList.subList(0, CREDIT_COUNT)
        }
        for (i in castList.indices) {
            val creaditInfoModel =
                CreaditInfoViewModel().apply {
                    setImageUrl(castList[i].profilePath)
                    setName(castList[i].name)
                    setPart(castList[i].character)
                }
            creaditInfoList.add(creaditInfoModel)
        }

        val spacing: Int = view.context.resources.getDimensionPixelSize(R.dimen.detail_overview_credit_divider)
        val recyclerViewDecoration = RecyclerViewDecoration(
            false,
            creaditInfoList.size,
            RecyclerViewSpacing(spacing, spacing, spacing, spacing)
        )
        view.addItemDecoration(recyclerViewDecoration)
        val adapter = view.adapter as CreditListAdapter
        adapter.setItem(creaditInfoList)
        adapter.notifyDataSetChanged()
    }
}

@BindingAdapter("detailCreditAdapter")
fun setDetailCreditAdapter(view: RecyclerView, adapter: CreditListAdapter?) {
    adapter?.let {
        val spacing: Int = view.context.resources.getDimensionPixelSize(R.dimen.detail_overview_credit_divider)
        val recyclerViewDecoration =
            RecyclerViewDecoration(true, 3, RecyclerViewSpacing(spacing, spacing, spacing, spacing))
        view.setHasFixedSize(true)
        view.addItemDecoration(recyclerViewDecoration)
        view.layoutManager = GridLayoutManager(view.context, 3)
        view.adapter = it
    }
}

@BindingAdapter("detailCreditCastItem")
fun setDetailCreditCastItem(view: RecyclerView, castList: List<CreditResponse.Cast>?) {
    castList?.let {
        val adapter = view.adapter as CreditListAdapter
        val creaditInfoList: MutableList<CreaditInfoViewModel> = mutableListOf()
        for (i in castList.indices) {
            val creaditInfoModel =
                CreaditInfoViewModel().apply {
                    setImageUrl(castList[i].profilePath)
                    setName(castList[i].name)
                    setPart(castList[i].character)
                }
            creaditInfoList.add(creaditInfoModel)
        }
        adapter.setItem(creaditInfoList)
        adapter.notifyDataSetChanged()
    }
}

@BindingAdapter("detailCreditCrewItem")
fun setDetailCreditCrewItem(view: RecyclerView, crewList: List<CreditResponse.Crew>?) {
    crewList?.let {
        val adapter = view.adapter as CreditListAdapter
        val creaditInfoList: MutableList<CreaditInfoViewModel> = mutableListOf()
        for (i in crewList.indices) {
            val creaditInfoModel = CreaditInfoViewModel().apply {
                setImageUrl(crewList[i].profilePath)
                setName(crewList[i].name)
                setPart(crewList[i].job)
            }
            creaditInfoList.add(creaditInfoModel)
        }
        adapter.setItem(creaditInfoList)
        adapter.notifyDataSetChanged()
    }
}