package com.movie.model.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.crashlytics.android.Crashlytics
import com.movie.common.constants.NOW_PALY_PAGER_COUNT
import com.movie.common.constants.PAGER_COUNT
import com.movie.common.constants.POPULAR_COUNT
import com.movie.common.utils.CommonUtil
import com.movie.customview.adapter.CustomListAdapter
import com.movie.customview.adapter.UpcomingPagerAdapter
import com.movie.customview.view.CustomIndicator
import com.movie.dialog.ProgressDialog
import com.movie.model.data.MovieMainResponse
import com.movie.model.request.ApiRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    private val apiRequest: ApiRequest,
    private val progress: ProgressDialog
) : BaseViewModel() {


    private val _cvIndicator = MutableLiveData<CustomIndicator>()
    private val _isRefresh = MutableLiveData<Boolean>()
    private val _pagerViewModelList = MutableLiveData<MutableList<PagerViewModel>>()
    private val _nowScreenList = MutableLiveData<List<MovieMainResponse.Movie>>()
    private val _popularList = MutableLiveData<List<MovieMainResponse.Movie>>()
    private val _topRatedList = MutableLiveData<List<MovieMainResponse.Movie>>()

    val nowScreenAdapter = CustomListAdapter()
    val popularAdapter = CustomListAdapter()
    val topRatedAdapter = CustomListAdapter()
    val upcomingPagerAdapter = UpcomingPagerAdapter()
    val cvIndicator: LiveData<CustomIndicator> get() = _cvIndicator
    val isRefresh: LiveData<Boolean> get() = _isRefresh

    val pagerViewModelList: LiveData<MutableList<PagerViewModel>> get() = _pagerViewModelList
    val nowScreenList: LiveData<List<MovieMainResponse.Movie>> get() = _nowScreenList
    val popularList: LiveData<List<MovieMainResponse.Movie>> get() = _popularList
    val topRatedList: LiveData<List<MovieMainResponse.Movie>> get() = _topRatedList

    init {
        requestApi()
    }

    fun setIndicator(cvIndicator: CustomIndicator) {
        _cvIndicator.value = cvIndicator
    }

    fun requestApi() {
        progress.show()
        addDisposable(
            apiRequest.getUpcoming(CommonUtil.getParam())
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
                        _pagerViewModelList.value = list
                    }
                    progressCancel()
                }, {
                    progressCancel()
                    Crashlytics.logException(it)
                })
        )

        addDisposable(
            apiRequest.getNowPlaying(CommonUtil.getParam())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (NOW_PALY_PAGER_COUNT < it.results.size) {
                        _nowScreenList.value = it.results.subList(0, NOW_PALY_PAGER_COUNT)
                    } else {
                        _nowScreenList.value = it.results
                    }
                    progressCancel()
                }, {
                    progressCancel()
                    Crashlytics.logException(it)
                })
        )

        addDisposable(
            apiRequest.getPopular(CommonUtil.getParam())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (POPULAR_COUNT < it.results.size) {
                        _popularList.value = it.results.subList(0, POPULAR_COUNT)
                    } else {
                        _popularList.value = it.results
                    }
                    progressCancel()
                }, {
                    progressCancel()
                    Crashlytics.logException(it)
                })
        )
        addDisposable(
            apiRequest.getTopRated(CommonUtil.getParam())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (POPULAR_COUNT < it.results.size) {
                        _topRatedList.value = it.results.subList(0, POPULAR_COUNT)
                    } else {
                        _topRatedList.value = it.results
                    }
                    progressCancel()
                }, {
                    progressCancel()
                    Crashlytics.logException(it)
                })
        )
        _isRefresh.value = false
    }

    override fun progressCancel() {
        super.progressCancel()
        if (count == 0 && progress.isShowing) {
            progress.cancel()
        }
    }
}