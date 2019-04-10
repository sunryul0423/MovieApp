package com.movie.model.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.movie.common.constants.NOW_PALY_PAGER_COUNT
import com.movie.common.constants.PAGER_COUNT
import com.movie.common.utils.CommonUtil
import com.movie.customview.adapter.CustomListAdapter
import com.movie.customview.adapter.UpcomingPagerAdapter
import com.movie.model.data.MovieMainResponse
import com.movie.model.request.ApiRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    private val apiRequest: ApiRequest,
    private val __upcomingPagerAdapter: UpcomingPagerAdapter,
    private val _customListAdapter: CustomListAdapter

) : BaseViewModel() {


    private val _upcomingPagerAdapter = MutableLiveData<UpcomingPagerAdapter>().apply { value = __upcomingPagerAdapter }
    private val _nowPlayingAdapter = MutableLiveData<CustomListAdapter>().apply { value = _customListAdapter }
    private val _upcomingMovieList = MutableLiveData<List<MovieMainResponse.Movie>>() //내부에서 변경
    private val _nowPlayingMovieList = MutableLiveData<List<MovieMainResponse.Movie>>() //내부에서 변경


    val upcomingPagerAdapter: LiveData<UpcomingPagerAdapter> get() = _upcomingPagerAdapter
    val nowPlayingAdapter: LiveData<CustomListAdapter> get() = _nowPlayingAdapter
    val upcomingMovieList: LiveData<List<MovieMainResponse.Movie>> get() = _upcomingMovieList //여기에 저장
    val nowPlayingMovieList: LiveData<List<MovieMainResponse.Movie>> get() = _nowPlayingMovieList //여기에 저장

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
                        _nowPlayingMovieList.value = it.results.subList(0, NOW_PALY_PAGER_COUNT)
                    } else {
                        _nowPlayingMovieList.value = it.results
                    }
                }, {
                    //차후 Fabric에 등록
                    Log.d("srpark", it.javaClass.simpleName + "실패 : " + it.toString())
                })
        )
    }
}