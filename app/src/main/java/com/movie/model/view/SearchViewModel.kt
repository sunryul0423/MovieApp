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

class SearchViewModel(private val apiRequest: ApiRequest, private val progress: ProgressDialog) : BaseViewModel() {

    private val _contents = MutableLiveData<String>()
    private val _searchList = MutableLiveData<MutableList<MovieMainResponse.Movie>>()
    private val _isAdd = MutableLiveData<Boolean>()


    val contents: LiveData<String> get() = _contents
    val searchList: LiveData<MutableList<MovieMainResponse.Movie>> get() = _searchList
    val isAdd: LiveData<Boolean> get() = _isAdd


    var correntPage: Int = 1
    val searchGridAdapter = SimilarGridAdapter()

    fun reqeustApi(searchPage: Int, add: Boolean) {
        progress.show()
        addDisposable(
            apiRequest.getSearch(CommonUtil.getSearchParam(_contents.value ?: ""), searchPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (searchPage < it.totalPages) {
                        correntPage++
                    }
                    _searchList.value = it.results
                    _isAdd.value = add
                    progress.cancel()
                }, {
                    progress.cancel()
                    Crashlytics.logException(it)
                })
        )
    }

    fun setContents(contents: String) {
        _contents.value = contents
    }
}