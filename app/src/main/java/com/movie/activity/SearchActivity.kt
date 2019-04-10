package com.movie.activity

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movie.R
import com.movie.`interface`.IRxResult
import com.movie.common.constants.SEARCH_SPAN_COUNT
import com.movie.common.utils.CommonUtil
import com.movie.customview.adapter.RecyclerViewDecoration
import com.movie.customview.adapter.SimilarGridAdapter
import com.movie.databinding.ActivitySearchBinding
import com.movie.model.data.MovieMainResponse
import com.movie.model.data.RecyclerViewSpacing

class SearchActivity : BaseActivity<ActivitySearchBinding>(), View.OnClickListener {
    override val layoutResourceId: Int
        get() = R.layout.activity_search

    private lateinit var rvSearch: RecyclerView
    private lateinit var etSearchTitle: EditText

    private var correntPage: Int = 1
    private lateinit var searchGridAdapter: SimilarGridAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView()
    }

    fun setView() {
        val llBack: LinearLayout = findViewById(R.id.ll_back)
        llBack.setOnClickListener(this)
        val llSearch: LinearLayout = findViewById(R.id.ll_search)
        llSearch.setOnClickListener(this)
        rvSearch = findViewById(R.id.rv_search)
        searchGridAdapter = SimilarGridAdapter(mContext, mutableListOf())

        rvSearch.setHasFixedSize(true)
        val spacing: Int = resources.getDimensionPixelSize(R.dimen.detail_search_grid_margin)
        val recyclerViewDecoration = RecyclerViewDecoration(
            true,
            SEARCH_SPAN_COUNT,
            RecyclerViewSpacing(spacing, spacing, spacing, spacing)
        )
        rvSearch.addItemDecoration(recyclerViewDecoration)
        rvSearch.layoutManager = GridLayoutManager(mContext, SEARCH_SPAN_COUNT)
        rvSearch.adapter = searchGridAdapter
        rvSearch.setOnScrollChangeListener { _, _, _, _, _ ->
            if (!rvSearch.canScrollVertically(1)) { //최하단 스크롤
                requestApi(correntPage)
            }
        }

        etSearchTitle = findViewById(R.id.et_search_title)
        etSearchTitle.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                llSearch.performClick()
            }
            return@setOnEditorActionListener false
        }

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ll_back -> {
                finish()
            }

            R.id.ll_search -> {
                // 검색 api
                requestApi(1)
            }
        }
    }

    fun requestApi(searchPage: Int) {
//        rxResponseManager.add(
//            apiRequest.getSearch(
//                CommonUtil.getSearchParam(etSearchTitle.text.toString()),
//                searchPage
//            ), object : IRxResult {
//
//                override fun <T> onNext(response: T) {
//                    val movieMainResponse = response as MovieMainResponse
//                    val searchList: MutableList<MovieMainResponse.Movie> = movieMainResponse.results
//                        ?: mutableListOf()
//                    if (searchPage < movieMainResponse.totalPages) {
//                        correntPage++
//                    }
//                    searchGridAdapter.addList(searchList)
//                }
//
//                override fun onErrer(error: Throwable) {
//                }
//            })
    }
}