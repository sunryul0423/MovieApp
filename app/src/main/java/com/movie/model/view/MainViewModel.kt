package com.movie.model.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.movie.common.constants.PAGER_COUNT
import com.movie.common.utils.CommonUtil
import com.movie.customview.adapter.UpcomingPagerAdapter
import com.movie.model.data.MovieMainResponse
import com.movie.model.request.ApiRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    private val apiRequest: ApiRequest,
    private val __upcomingPagerAdapter: UpcomingPagerAdapter

) : BaseViewModel() {


    private val _movieList = MutableLiveData<List<MovieMainResponse.Movie>>() //내부에서 변경
    private val _upcomingPagerAdapter = MutableLiveData<UpcomingPagerAdapter>().apply { value = __upcomingPagerAdapter }

    val movieList: LiveData<List<MovieMainResponse.Movie>> = _movieList //여기에 저장
    val upcomingPagerAdapter: MutableLiveData<UpcomingPagerAdapter> = _upcomingPagerAdapter

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
                        _movieList.value = it.results.subList(0, PAGER_COUNT)
                    } else {
                        _movieList.value = it.results
                    }
                }, {
                    //차후 Fabric에 등록
                    Log.d("srpark", it.javaClass.simpleName + "실패 : " + it.toString())
                })
        )
    }
}