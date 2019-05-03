package com.movie.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.ViewModelProviders
import com.movie.R
import com.movie.databinding.ActivitySearchBinding
import com.movie.model.view.SearchViewModel
import com.movie.model.view.SearchViewModelFactory

class SearchActivity : BaseActivity<ActivitySearchBinding>(), View.OnClickListener {
    override val layoutResourceId: Int
        get() = R.layout.activity_search

    private lateinit var searchViewModelFactory: SearchViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        searchViewModelFactory = SearchViewModelFactory(apiRequest, progress)
        val searchViewModel = ViewModelProviders.of(this, searchViewModelFactory).get(SearchViewModel::class.java)
        viewBinding.searchViewModel = searchViewModel
        viewBinding.lifecycleOwner = this
        setView()
    }

    fun setView() {
        viewBinding.llBack.setOnClickListener(this)
        viewBinding.etSearchTitle.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewBinding.searchViewModel?.setContents(s.toString())
            }
        })

        viewBinding.etSearchTitle.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewBinding.llSearch.performClick()
            }
            return@setOnEditorActionListener false
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ll_back -> {
                finish()
            }
        }
    }
}