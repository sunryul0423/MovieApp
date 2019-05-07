package com.movie.model.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.crashlytics.android.Crashlytics
import com.movie.common.utils.CommonUtil
import com.movie.customview.adapter.SimilarGridAdapter
import com.movie.dialog.ProgressDialog
import com.movie.model.data.MovieMainResponse
import com.movie.model.request.ApiRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailSimilarViewModel(
    private val apiRequest: ApiRequest,
    private val progress: ProgressDialog,
    private val movieId: Int
) : BaseViewModel() {

    private val _similarList = MutableLiveData<List<MovieMainResponse.Movie>>()
    private val _isAdd = MutableLiveData<Boolean>()

    val similarList: LiveData<List<MovieMainResponse.Movie>> get() = _similarList
    val isAdd: LiveData<Boolean> get() = _isAdd
    val similarGridAdapter = SimilarGridAdapter()

    init {
        requestApi(false)
    }

    fun requestApi(isAdd: Boolean) {
        addDisposable(
            apiRequest.getSimilar(movieId, CommonUtil.getParam())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _similarList.value = it.results
                    _isAdd.value = isAdd
                }, {
                    Crashlytics.logException(it)
                })

        )
    }
}