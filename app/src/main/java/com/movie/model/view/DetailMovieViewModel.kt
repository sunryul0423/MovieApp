package com.movie.model.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.crashlytics.android.Crashlytics
import com.movie.common.constants.IMAGE_URL
import com.movie.common.utils.CommonUtil
import com.movie.model.data.MovieDetailResponse
import com.movie.model.request.ApiRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailMovieViewModel(
    private val apiRequest: ApiRequest,
    private val movieId: Int
) : BaseViewModel() {

    private val _movieDetailResponse = MutableLiveData<MovieDetailResponse>()
    private val _title = MutableLiveData<String>()
    private val _originalTitle = MutableLiveData<String>()
    private val _voteAverage = MutableLiveData<String>()
    private val _backdropPath = MutableLiveData<String>()
    private val _posterPath = MutableLiveData<String>()
    private val _genresString = MutableLiveData<String>()

    val movieDetailResponse: LiveData<MovieDetailResponse> get() = _movieDetailResponse
    val title: LiveData<String> get() = _title
    val originalTitle: LiveData<String> get() = _originalTitle
    val voteAverage: LiveData<String> get() = _voteAverage
    val backdropPath: LiveData<String> get() = _backdropPath
    val posterPath: LiveData<String> get() = _posterPath
    val genresString: LiveData<String> get() = _genresString

    init {
        reqeustApi()
    }

    fun reqeustApi() {
        addDisposable(
            apiRequest.getDetail(movieId, CommonUtil.getParam())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _movieDetailResponse.value = it
                    _title.value = it.title
                    _originalTitle.value = it.originalTitle
                    _voteAverage.value = "${it.voteAverage}"
                    _backdropPath.value = IMAGE_URL + it.backdropPath
                    _posterPath.value = IMAGE_URL + it.posterPath

                    val stringBuilder = StringBuilder()
                    for (i in 0 until it.genres.size) {
                        stringBuilder.append(it.genres[i].name)
                        if (it.genres.size != 1 && i != it.genres.size - 1) {
                            stringBuilder.append(", ")
                        }
                    }
                    _genresString.value = stringBuilder.toString()


                }, {
                    Crashlytics.logException(it)
                })
        )
    }
}