package com.movie.model.view

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.movie.interfaces.ApiRequest
import com.movie.model.data.MovieMainResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchViewModel(private val apiRequest: ApiRequest) : BaseViewModel() {

    private val config = PagedList.Config.Builder()
        .setInitialLoadSizeHint(20)
        .setPageSize(20)
        .setPrefetchDistance(10)
        .setEnablePlaceholders(true)
        .build()


    fun getDataSource(searchText: String): LiveData<PagedList<MovieMainResponse.Movie>> {
        val liveDataBuilder = LivePagedListBuilder(object : DataSource.Factory<Int, MovieMainResponse.Movie>() {
            override fun create(): DataSource<Int, MovieMainResponse.Movie> {
                return SearchPageKeyedDataSource(searchText)
            }
        }, config)
        return liveDataBuilder.build()
    }

    inner class SearchPageKeyedDataSource(private val searchText: String) :
        PageKeyedDataSource<Int, MovieMainResponse.Movie>() {

        private var pageNum = 1

        override fun loadInitial(
            params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, MovieMainResponse.Movie>
        ) {
            progress.postValue(true)
            addDisposable(
                apiRequest.getSearch(searchText, pageNum)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        pageNum++
                        callback.onResult(response.results, null, response.totalPages)
                        progress.value = false
                    }, {
                        onError(it)
                    })
            )
        }

        override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieMainResponse.Movie>) {
            if (pageNum <= params.key) {
                progress.postValue(true)
                addDisposable(
                    apiRequest.getSearch(searchText, pageNum)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ response ->
                            pageNum++
                            callback.onResult(response.results, response.totalPages)
                            progress.value = false
                        }, {
                            onError(it)
                        })
                )
            }
        }

        override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieMainResponse.Movie>) {
        }
    }
}