package com.movie.model.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.movie.customview.adapter.CreditListAdapter
import com.movie.customview.adapter.VideoListAdapter
import com.movie.interfaces.ApiRequest
import com.movie.model.data.CreditResponse
import com.movie.model.data.VideoResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailOverviewViewModel(private val apiRequest: ApiRequest) : BaseViewModel() {

    private val creditResponse = MutableLiveData<CreditResponse>()
    private val isVisibleMore = MutableLiveData<Boolean>()
    private val overview = MutableLiveData<String>()
    private val videoList = MutableLiveData<List<VideoResponse.Videos>>()


    val videoListAdapter = VideoListAdapter()
    val creditListAdapter = CreditListAdapter()

    fun requestOverviewApi(movieId: Int, overview: String) {
        progress.value = true
        this.overview.value = overview
        addDisposable(
            apiRequest.getVideos(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    videoList.value = it.results
                    progress.value = false
                }, {
                    onError(it)
                })
        )

        addDisposable(
            apiRequest.getCredit(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    creditResponse.value = it
                    progress.value = false
                }, {
                    onError(it)
                })
        )
    }

    fun setVisibleMore(isVisibleMore: Boolean) {
        this.isVisibleMore.value = isVisibleMore
    }

    fun getCreditResponse(): LiveData<CreditResponse> {
        return creditResponse
    }

    fun isVisibleMore(): LiveData<Boolean> {
        return isVisibleMore
    }

    fun getOverview(): LiveData<String> {
        return overview
    }

    fun getVideoList(): LiveData<List<VideoResponse.Videos>> {
        return videoList
    }

}