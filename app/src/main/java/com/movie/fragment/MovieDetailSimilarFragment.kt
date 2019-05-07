package com.movie.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
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
import com.movie.databinding.FragmentDetailSimilarBinding
import com.movie.model.data.MovieMainResponse
import com.movie.model.data.RecyclerViewSpacing
import com.movie.model.view.DetailSimilarViewModel
import com.movie.model.view.DetailSimilarViewModelFactory

class MovieDetailSimilarFragment : BaseFragment<FragmentDetailSimilarBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_detail_similar

    private lateinit var detailSimilarViewModelFactory: DetailSimilarViewModelFactory

    private var movieId: Int = 0

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
        detailSimilarViewModelFactory = DetailSimilarViewModelFactory(apiRequest, progress, movieId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        val detailSimilarViewModel = ViewModelProviders.of(this, detailSimilarViewModelFactory).get(
            DetailSimilarViewModel::class.java
        )
        viewBinding.detailSimilarViewModel = detailSimilarViewModel
        viewBinding.lifecycleOwner = this

        return view
    }
}