package com.movie.model.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.movie.customview.adapter.CustomListAdapter
import com.movie.customview.adapter.UpcomingPagerAdapter
import com.movie.model.request.ApiRequest

class MainViewModelFactory(
    private val apiRequest: ApiRequest,
    private val upcomingPagerAdapter: UpcomingPagerAdapter,
    private val customListAdapter: CustomListAdapter
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(apiRequest, upcomingPagerAdapter, customListAdapter) as T
    }
}