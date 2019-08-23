package com.movie.model.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.movie.customview.adapter.SimilarGridAdapter
import com.movie.interfaces.ApiRequest
import com.movie.model.data.MovieMainResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchViewModel(private val apiRequest: ApiRequest) : BaseViewModel() {

    private var contents = ""
    private val searchList = MutableLiveData<List<MovieMainResponse.Movie>>()
    private val isAdd = MutableLiveData<Boolean>()


    var currentPage: Int = 1
    val searchGridAdapter = SimilarGridAdapter()

    fun requestSearchApi(searchPage: Int, add: Boolean) {
        progress.value = true
        addDisposable(
            apiRequest.getSearch(contents, searchPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (searchPage < it.totalPages) {
                        currentPage++
                    }
                    searchList.value = it.results
                    isAdd.value = add
                    progress.value = false
                }, {
                    onError(it)
                })
        )
    }

    fun setContents(contents: String) {
        this.contents = contents
    }

    fun getSearchList(): LiveData<List<MovieMainResponse.Movie>> {
        return searchList
    }

    fun isAdd(): LiveData<Boolean> {
        return isAdd
    }
}