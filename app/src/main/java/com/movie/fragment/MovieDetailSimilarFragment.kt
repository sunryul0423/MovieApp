package com.movie.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.movie.R
import com.movie.activity.DetailMovieActivity
import com.movie.common.MOVIE_ID
import com.movie.common.showThrowableToast
import com.movie.databinding.FragmentDetailSimilarBinding
import com.movie.interfaces.IScrollChangeListener
import com.movie.model.view.DetailSimilarViewModel
import com.movie.model.view.DetailSimilarViewModelFactory
import org.koin.android.ext.android.inject

class MovieDetailSimilarFragment : BaseFragment<FragmentDetailSimilarBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_detail_similar

    private val detailSimilarViewModelFactory: DetailSimilarViewModelFactory by inject()

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
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        val detailSimilarViewModel =
            ViewModelProvider(this, detailSimilarViewModelFactory).get(DetailSimilarViewModel::class.java).apply {
                this.requestApi(movieId, false)
            }
        viewBinding.detailSimilarViewModel = detailSimilarViewModel
        viewBinding.lifecycleOwner = this

        liveDataObserver(detailSimilarViewModel)

        return view
    }

    private fun liveDataObserver(detailSimilarViewModel: DetailSimilarViewModel) {
        detailSimilarViewModel.getProgress().observe(this, Observer {
            if (it)
                progress.show()
            else
                progress.cancel()
        })
        detailSimilarViewModel.getThrowableData().observe(this, Observer {
            showThrowableToast(mContext, it)
        })
    }
}