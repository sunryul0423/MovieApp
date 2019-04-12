package com.movie.model.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.movie.common.constants.NOW_PALY_PAGER_COUNT
import com.movie.common.constants.PAGER_COUNT
import com.movie.common.constants.POPULAR_COUNT
import com.movie.common.utils.CommonUtil
import com.movie.customview.adapter.CustomListAdapter
import com.movie.customview.adapter.UpcomingPagerAdapter
import com.movie.model.data.MovieMainResponse
import com.movie.model.request.ApiRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    private val apiRequest: ApiRequest,
    private val __upcomingPagerAdapter: UpcomingPagerAdapter
) : BaseViewModel() {

    private val _isRefresh = MutableLiveData<Boolean>()
    private val _upcomingPagerAdapter = MutableLiveData<UpcomingPagerAdapter>().apply { value = __upcomingPagerAdapter }

    private val _upcomingMovieList = MutableLiveData<List<MovieMainResponse.Movie>>()
    private val _nowScreenList = MutableLiveData<List<MovieMainResponse.Movie>>()
    private val _popularList = MutableLiveData<List<MovieMainResponse.Movie>>()
    private val _topRatedList = MutableLiveData<List<MovieMainResponse.Movie>>()


    val upcomingPagerAdapter: LiveData<UpcomingPagerAdapter> get() = _upcomingPagerAdapter
    val nowScreenAdapter: CustomListAdapter = CustomListAdapter()
    val popularAdapter: CustomListAdapter = CustomListAdapter()
    val topRatedAdapter: CustomListAdapter = CustomListAdapter()

    val isRefresh: LiveData<Boolean> get() = _isRefresh
    val upcomingMovieList: LiveData<List<MovieMainResponse.Movie>> get() = _upcomingMovieList
    val nowScreenList: LiveData<List<MovieMainResponse.Movie>> get() = _nowScreenList
    val popularList: LiveData<List<MovieMainResponse.Movie>> get() = _popularList
    val topRatedList: LiveData<List<MovieMainResponse.Movie>> get() = _topRatedList

    init {
        requestApi()
    }



    fun requestApi() {
//        var progress: ProgressDialog = ProgressDialog(mContext)

        addDisposable(
            apiRequest.getUpcoming(CommonUtil.getParam())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (PAGER_COUNT < it.results.size) {
                        _upcomingMovieList.value = it.results.subList(0, PAGER_COUNT)
                    } else {
                        _upcomingMovieList.value = it.results
                    }
                }, {
                    //차후 Fabric에 등록
                    Log.d("srpark", it.javaClass.simpleName + "실패 : " + it.toString())
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
                }, {
                    //차후 Fabric에 등록
                    Log.d("srpark", it.javaClass.simpleName + "실패 : " + it.toString())
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
                }, {
                    //차후 Fabric에 등록
                    Log.d("srpark", it.javaClass.simpleName + "실패 : " + it.toString())
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
                }, {
                    //차후 Fabric에 등록
                    Log.d("srpark", it.javaClass.simpleName + "실패 : " + it.toString())
                })
        )

    }
}