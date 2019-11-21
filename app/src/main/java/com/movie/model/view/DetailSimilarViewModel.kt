package com.movie.model.view

import androidx.paging.PageKeyedDataSource
import com.movie.interfaces.ApiRequest
import com.movie.model.data.MovieMainResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailSimilarViewModel(private val apiRequest: ApiRequest) : BaseViewModel() {

    fun getDataSource(movieId: Int): SearchPageKeyedDataSource {
        return SearchPageKeyedDataSource(movieId)
    }

    inner class SearchPageKeyedDataSource(private val movieId: Int) :
        PageKeyedDataSource<Int, MovieMainResponse.Movie>() {

        override fun loadInitial(
            params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, MovieMainResponse.Movie>
        ) {
            progress.postValue(true)
            addDisposable(
                apiRequest.getSimilar(movieId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        callback.onResult(response.results, null, 0)
                        progress.value = false
                    }, {
                        onError(it)
                    })
            )
        }

        override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieMainResponse.Movie>) {
        }

        override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieMainResponse.Movie>) {
        }
    }
}