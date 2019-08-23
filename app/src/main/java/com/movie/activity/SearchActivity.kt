package com.movie.activity

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.movie.R
import com.movie.util.showThrowableToast
import com.movie.databinding.ActivitySearchBinding
import com.movie.model.view.SearchViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class SearchActivity : BaseActivity<ActivitySearchBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_search


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val searchViewModel: SearchViewModel by viewModel()
        viewBinding.searchViewModel = searchViewModel
        viewBinding.lifecycleOwner = this

        liveDataObserver(searchViewModel)
        rxEvent(searchViewModel)
    }

    private fun rxEvent(searchViewModel: SearchViewModel) {
        searchViewModel.addDisposable(
            RxView.clicks(ll_back)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe { finish() }
        )

        searchViewModel.addDisposable(
            RxTextView.textChanges(et_search_title)
                .filter { text -> text.isNotEmpty() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { text -> searchViewModel.setContents(text.toString()) }
        )

        searchViewModel.addDisposable(
            RxTextView.editorActionEvents(et_search_title)
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
                .filter { et_search_title.text.toString().isNotEmpty() }
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe { searchViewModel.requestSearchApi(1, false) }
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