package com.movie.customview.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.movie.R
import com.movie.activity.DetailMovieActivity
import com.movie.util.MOVIE_ID
import com.movie.databinding.ViewPagerLayoutBinding
import com.movie.model.view.PagerViewModel

class UpcomingPagerAdapter : PagerAdapter() {

    private var pagerViewModelList: MutableList<PagerViewModel> = mutableListOf()

    fun setItem(_pagerViewModelList: MutableList<PagerViewModel>) {
        pagerViewModelList = if (_pagerViewModelList.isNullOrEmpty()) {
            mutableListOf()
        } else {
            _pagerViewModelList
        }
        notifyDataSetChanged()
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun getCount(): Int {
        return pagerViewModelList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding: ViewPagerLayoutBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(container.context),
                R.layout.view_pager_layout,
                container,
                false
            )
        binding.pagerViewModel = pagerViewModelList[position]

        binding.flPagerItem.setOnClickListener {
            val intent = Intent(container.context, DetailMovieActivity::class.java)
            intent.putExtra(MOVIE_ID, pagerViewModelList[position].id.value)
            ActivityCompat.startActivity(container.context, intent, null)
        }
        container.addView(binding.root, position)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}