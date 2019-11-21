package com.movie.customview.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.movie.R
import com.movie.customview.view.CustomIndicator
import com.movie.model.data.CreditResponse
import com.movie.model.data.MovieMainResponse
import com.movie.model.data.RecyclerViewSpacing
import com.movie.model.data.VideoResponse
import com.movie.model.view.CreditInfoViewModel
import com.movie.model.view.PagerViewModel
import com.movie.util.CREDIT_COUNT
import com.movie.util.PAGER_COUNT

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
    }
}

@BindingAdapter("pageAdapter", "indicator")
fun setPagerAdapter(view: ViewPager, upcomingPagerAdapter: UpcomingPagerAdapter?, indicator: CustomIndicator?) {
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
                    indicator?.selectDot(position)
                }
            })
        }
    }
}

@BindingAdapter("pageItem")
fun setPageItem(view: ViewPager, pagerViewModelList: MutableList<PagerViewModel>?) {
    pagerViewModelList?.let {
        (view.adapter as UpcomingPagerAdapter).run {
            this.setItem(it)
        }
    }
}

@BindingAdapter("imgUrl")
fun setImageUrl(view: ImageView, url: String?) {
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

@BindingAdapter("creditImgUrl")
fun setCreditImgUrl(view: ImageView, url: String?) {
    Glide.with(view.context)
        .load(url)
        .override(600, 600)
        .centerCrop()
        .apply(RequestOptions.circleCropTransform())
        .placeholder(R.drawable.profile)
        .error(R.drawable.profile)
        .into(view)
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

        val creditInfoList: MutableList<CreditInfoViewModel> = mutableListOf()
        var castList = it.cast
        val crewList = it.crew

        for (i in crewList.indices) {
            if ("Director" == crewList[i].job) {
                val creditInfoModel =
                    CreditInfoViewModel().apply {
                        setImageUrl(crewList[i].profilePath)
                        setName(crewList[i].name)
                        setPart(crewList[i].job)
                    }
                creditInfoList.add(creditInfoModel)
                break
            }
        }

        if (CREDIT_COUNT < castList.size) {
            castList = castList.subList(0, CREDIT_COUNT)
        }
        for (i in castList.indices) {
            val creditInfoModel =
                CreditInfoViewModel().apply {
                    setImageUrl(castList[i].profilePath)
                    setName(castList[i].name)
                    setPart(castList[i].character)
                }
            creditInfoList.add(creditInfoModel)
        }

        val spacing: Int = view.context.resources.getDimensionPixelSize(R.dimen.detail_overview_credit_divider)
        val recyclerViewDecoration = RecyclerViewDecoration(
            false,
            creditInfoList.size,
            RecyclerViewSpacing(spacing, spacing, spacing, spacing)
        )
        view.addItemDecoration(recyclerViewDecoration)
        val adapter = view.adapter as CreditListAdapter
        adapter.setItem(creditInfoList)
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
        val creditInfoList: MutableList<CreditInfoViewModel> = mutableListOf()
        for (i in castList.indices) {
            val creditInfoModel =
                CreditInfoViewModel().apply {
                    setImageUrl(castList[i].profilePath)
                    setName(castList[i].name)
                    setPart(castList[i].character)
                }
            creditInfoList.add(creditInfoModel)
        }
        adapter.setItem(creditInfoList)
    }
}

@BindingAdapter("detailCreditCrewItem")
fun setDetailCreditCrewItem(view: RecyclerView, crewList: List<CreditResponse.Crew>?) {
    crewList?.let {
        val adapter = view.adapter as CreditListAdapter
        val creditInfoList: MutableList<CreditInfoViewModel> = mutableListOf()
        for (i in crewList.indices) {
            val creditInfoModel = CreditInfoViewModel().apply {
                setImageUrl(crewList[i].profilePath)
                setName(crewList[i].name)
                setPart(crewList[i].job)
            }
            creditInfoList.add(creditInfoModel)
        }
        adapter.setItem(creditInfoList)
    }
}