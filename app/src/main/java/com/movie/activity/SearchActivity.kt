package com.movie.activity

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.movie.R
import com.movie.customview.adapter.RecyclerViewDecoration
import com.movie.customview.adapter.SimilarGridAdapter
import com.movie.databinding.ActivitySearchBinding
import com.movie.model.data.RecyclerViewSpacing
import com.movie.model.view.SearchViewModel
import com.movie.util.SEARCH_SPAN_COUNT
import com.movie.util.showThrowableToast
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class SearchActivity : BaseActivity<ActivitySearchBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_search

    private val searchGridAdapter = SimilarGridAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val searchViewModel: SearchViewModel by viewModel()
        viewBinding.searchViewModel = searchViewModel
        viewBinding.lifecycleOwner = this

        viewBinding.rvSearch.run {
            val spacing = resources.getDimensionPixelSize(R.dimen.detail_search_grid_margin)
            val recyclerViewDecoration = RecyclerViewDecoration(
                true,
                SEARCH_SPAN_COUNT,
                RecyclerViewSpacing(spacing, spacing, spacing, spacing)
            )
            setHasFixedSize(false)
            addItemDecoration(recyclerViewDecoration)
            layoutManager = GridLayoutManager(this@SearchActivity, SEARCH_SPAN_COUNT)
            adapter = searchGridAdapter
        }

        liveDataObserver(searchViewModel)
        rxEvent(searchViewModel)
    }

    private fun rxEvent(searchViewModel: SearchViewModel) {
        searchViewModel.addDisposable(
            RxView.clicks(ll_back)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe { finish() }
        )

//        searchViewModel.addDisposable(
//            RxTextView.textChanges(etSearchTitle)
//                .filter { text -> text.isNotEmpty() }
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe { text ->
//                }
//        )

        searchViewModel.addDisposable(
            RxTextView.editorActionEvents(etSearchTitle)
                .observeOn(AndroidSchedulers.mainThread())
                .filter { it.view().text.isNotEmpty() }
                .subscribe {
                    if (it.actionId() == EditorInfo.IME_ACTION_SEARCH) {
                        viewBinding.llSearch.performClick()
                    }
                }
        )

        searchViewModel.addDisposable(
            RxView.clicks(ll_search)
                .filter { etSearchTitle.text.toString().isNotEmpty() }
                .map { return@map etSearchTitle.text.toString() }
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe { text ->
                    searchViewModel.getDataSource(text).observe(this, Observer(searchGridAdapter::submitList))
                }
        )
    }

    private fun liveDataObserver(searchViewModel: SearchViewModel) {
        searchViewModel.getProgress().observe(this, Observer {
            if (it)
                progress.show()
            else
                progress.cancel()
        })
        searchViewModel.getThrowableData().observe(this, Observer {
            showThrowableToast(this@SearchActivity, it)
        })
    }
}