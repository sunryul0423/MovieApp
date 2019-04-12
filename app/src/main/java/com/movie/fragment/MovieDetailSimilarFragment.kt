package com.movie.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movie.R
import com.movie.interfaces.IRxResult
import com.movie.interfaces.IScrollChangeListener
import com.movie.activity.DetailMovieActivity
import com.movie.common.constants.MOVIE_ID
import com.movie.common.utils.CommonUtil
import com.movie.customview.adapter.RecyclerViewDecoration
import com.movie.customview.adapter.SimilarGridAdapter
import com.movie.model.data.MovieMainResponse
import com.movie.model.data.RecyclerViewSpacing

class MovieDetailSimilarFragment : BaseFragment() {

    private var movieId: Int = 0

    private lateinit var rvSimilar: RecyclerView
    private lateinit var scrollChangeListener: IScrollChangeListener

    companion object {
        fun newInstance(movieId: Int): MovieDetailSimilarFragment {
            val fragment = MovieDetailSimilarFragment()
            val args = Bundle()
            args.putInt(MOVIE_ID, movieId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        scrollChangeListener = mContext as DetailMovieActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = arguments?.getInt(MOVIE_ID) ?: 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail_similar, container, false)
        setView(view)
        requestApi()
        return view
    }

    fun setView(view: View) {
        rvSimilar = view.findViewById(R.id.rv_similar)
        rvSimilar.setHasFixedSize(false)
        val spacing: Int = resources.getDimensionPixelSize(R.dimen.detail_overview_credit_divider)
        val recyclerViewDecoration =
            RecyclerViewDecoration(true, 2, RecyclerViewSpacing(spacing, spacing, spacing, spacing))
        rvSimilar.addItemDecoration(recyclerViewDecoration)
        rvSimilar.layoutManager = GridLayoutManager(mContext, 2)
    }

    private fun requestApi() {
        rxResponseManager.add(apiRequest.getSimilar(movieId, CommonUtil.getParam()), object : IRxResult {

            override fun <T> onNext(response: T) {
                val similarList: MutableList<MovieMainResponse.Movie> = (response as MovieMainResponse).results
                val similarGridAdapter = SimilarGridAdapter(mContext, similarList)
                rvSimilar.adapter = similarGridAdapter
            }

            override fun onErrer(error: Throwable) {
            }
        })
    }


}