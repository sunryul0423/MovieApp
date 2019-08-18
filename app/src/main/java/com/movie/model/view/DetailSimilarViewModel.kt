package com.movie.model.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.movie.customview.adapter.SimilarGridAdapter
import com.movie.interfaces.ApiRequest
import com.movie.model.data.MovieMainResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailSimilarViewModel(private val apiRequest: ApiRequest) : BaseViewModel() {
    private val similarList = MutableLiveData<List<MovieMainResponse.Movie>>()
    private val isAdd = MutableLiveData<Boolean>()

    val similarGridAdapter = SimilarGridAdapter()

    fun requestApi(movieId: Int, isAdd: Boolean) {
        progress.value = true
        addDisposable(
            apiRequest.getSimilar(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    similarList.value = it.results
                    this.isAdd.value = isAdd
                    progress.value = false
                }, {
                    onError(it)
                })

        )
    }

    fun getSimilarList(): LiveData<List<MovieMainResponse.Movie>> {
        return similarList
    }

    fun isAdd(): LiveData<Boolean> {
        return isAdd
    }
}