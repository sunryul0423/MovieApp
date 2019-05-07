package com.movie.model.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.crashlytics.android.Crashlytics
import com.movie.common.constants.API_KEY
import com.movie.common.constants.TMDB_API_KEY
import com.movie.common.utils.CommonUtil
import com.movie.customview.adapter.CreditListAdapter
import com.movie.customview.adapter.VideoListAdapter
import com.movie.model.data.CreditResponse
import com.movie.model.data.MovieDetailResponse
import com.movie.model.data.VideoResponse
import com.movie.model.request.ApiRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailOverviewViewModel(
    private val apiRequest: ApiRequest,
    private val movieDetailResponse: MovieDetailResponse,
    private val movieId: Int
) : BaseViewModel() {

    private val _creditResponse = MutableLiveData<CreditResponse>()
    private val _isVisibleMore = MutableLiveData<Boolean>()
    private val _overview = MutableLiveData<String>().apply { value = movieDetailResponse.overview }
    private val _videoList = MutableLiveData<List<VideoResponse.Videos>>()

    val creditResponse: LiveData<CreditResponse> get() = _creditResponse
    val isVisibleMore: LiveData<Boolean> get() = _isVisibleMore
    val overview: LiveData<String> get() = _overview
    val videoList: LiveData<List<VideoResponse.Videos>> get() = _videoList
    val videoListAdapter = VideoListAdapter()
    val creditListAdapter = CreditListAdapter()

    init {
        requestApi()
    }

    fun requestApi() {
        addDisposable(
            apiRequest.getVideos(movieId, hashMapOf(API_KEY to TMDB_API_KEY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _videoList.value = it.results
                }, {
                    Crashlytics.logException(it)
                })
        )

        addDisposable(
            apiRequest.getCredit(movieId, CommonUtil.getParam())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _creditResponse.value = it
                }, {
                    Crashlytics.logException(it)
                })
        )
    }

    fun setVisibleMore(isVisibleMore: Boolean) {
        _isVisibleMore.value = isVisibleMore
    }
}