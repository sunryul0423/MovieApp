package com.movie.model.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.movie.util.NOW_PLAY_PAGER_COUNT
import com.movie.util.PAGER_COUNT
import com.movie.util.POPULAR_COUNT
import com.movie.customview.adapter.CustomListAdapter
import com.movie.customview.adapter.UpcomingPagerAdapter
import com.movie.customview.view.CustomIndicator
import com.movie.interfaces.ApiRequest
import com.movie.model.data.MovieMainResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val apiRequest: ApiRequest) : BaseViewModel() {

    private val cvIndicator = MutableLiveData<CustomIndicator>()
    private val isRefresh = MutableLiveData<Boolean>()
    private val pagerViewModelList = MutableLiveData<MutableList<PagerViewModel>>()
    private val nowScreenList = MutableLiveData<List<MovieMainResponse.Movie>>()
    private val popularList = MutableLiveData<List<MovieMainResponse.Movie>>()
    private val topRatedList = MutableLiveData<List<MovieMainResponse.Movie>>()
    private val upcomingPagerAdapter = MutableLiveData<UpcomingPagerAdapter>()

    val nowScreenAdapter = CustomListAdapter()
    val popularAdapter = CustomListAdapter()
    val topRatedAdapter = CustomListAdapter()

    init {
        requestApi()
    }

    fun requestApi() {
        upcomingPagerAdapter.value = UpcomingPagerAdapter()
        cvIndicator.value?.selectDot(0)
        progress.value = true
        addDisposable(
            apiRequest.getUpcoming()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val upcomingMovieList: List<MovieMainResponse.Movie> = if (PAGER_COUNT < it.results.size) {
                        it.results.subList(0, PAGER_COUNT)
                    } else {
                        it.results
                    }

                    val list: MutableList<PagerViewModel> = mutableListOf()
                    for (position in upcomingMovieList.indices) {
                        val pagerViewModel = PagerViewModel().apply {
                            setTitle(upcomingMovieList[position].title)
                            setImageUrl(upcomingMovieList[position].backdropPath)
                            setId(upcomingMovieList[position].id)
                        }
                        list.add(pagerViewModel)
                        pagerViewModelList.value = list
                    }
                    progress.value = false
                }, {
                    onError(it)
                })
        )

        addDisposable(
            apiRequest.getNowPlaying()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (NOW_PLAY_PAGER_COUNT < it.results.size) {
                        nowScreenList.value = it.results.subList(0, NOW_PLAY_PAGER_COUNT)
                    } else {
                        nowScreenList.value = it.results
                    }
                    progress.value = false
                }, {
                    onError(it)
                })
        )

        addDisposable(
            apiRequest.getPopular()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (POPULAR_COUNT < it.results.size) {
                        popularList.value = it.results.subList(0, POPULAR_COUNT)
                    } else {
                        popularList.value = it.results
                    }
                    progress.value = false
                }, {
                    onError(it)
                })
        )
        addDisposable(
            apiRequest.getTopRated()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (POPULAR_COUNT < it.results.size) {
                        topRatedList.value = it.results.subList(0, POPULAR_COUNT)
                    } else {
                        topRatedList.value = it.results
                    }
                    progress.value = false
                }, {
                    onError(it)
                })
        )
        isRefresh.value = false
    }

    fun setIndicator(cvIndicator: CustomIndicator) {
        this.cvIndicator.value = cvIndicator
    }

    fun getUpcomingPagerAdapter(): LiveData<UpcomingPagerAdapter> {
        return upcomingPagerAdapter
    }

    fun getCvIndicator(): LiveData<CustomIndicator> {
        return cvIndicator
    }

    fun isRefresh(): LiveData<Boolean> {
        return isRefresh
    }

    fun getPagerViewModelList(): LiveData<MutableList<PagerViewModel>> {
        return pagerViewModelList
    }

    fun getNowScreenList(): LiveData<List<MovieMainResponse.Movie>> {
        return nowScreenList
    }

    fun getPopularList(): LiveData<List<MovieMainResponse.Movie>> {
        return popularList
    }

    fun getTopRatedList(): LiveData<List<MovieMainResponse.Movie>> {
        return topRatedList
    }
}