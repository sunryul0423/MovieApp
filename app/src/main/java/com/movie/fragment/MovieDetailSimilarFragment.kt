package com.movie.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import androidx.recyclerview.widget.GridLayoutManager
import com.movie.R
import com.movie.activity.DetailMovieActivity
import com.movie.customview.adapter.RecyclerViewDecoration
import com.movie.customview.adapter.SimilarGridAdapter
import com.movie.databinding.FragmentDetailSimilarBinding
import com.movie.interfaces.IScrollChangeListener
import com.movie.model.data.MovieMainResponse
import com.movie.model.data.RecyclerViewSpacing
import com.movie.model.view.DetailSimilarViewModel
import com.movie.util.MOVIE_ID
import com.movie.util.SEARCH_SPAN_COUNT
import com.movie.util.showThrowableToast
import kotlinx.android.synthetic.main.fragment_detail_similar.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailSimilarFragment : BaseFragment<FragmentDetailSimilarBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_detail_similar

    private var movieId: Int = 0

    private lateinit var scrollChangeListener: IScrollChangeListener
    private val searchGridAdapter = SimilarGridAdapter()

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
        val detailSimilarViewModel: DetailSimilarViewModel by viewModel()
        viewBinding.detailSimilarViewModel = detailSimilarViewModel
        viewBinding.lifecycleOwner = this

        viewBinding.rvSimilar.run {
            val spacing = mContext.resources.getDimensionPixelSize(R.dimen.detail_search_grid_margin)
            val recyclerViewDecoration = RecyclerViewDecoration(
                true,
                SEARCH_SPAN_COUNT,
                RecyclerViewSpacing(spacing, spacing, spacing, spacing)
            )
            setHasFixedSize(false)
            addItemDecoration(recyclerViewDecoration)
            layoutManager = GridLayoutManager(mContext, SEARCH_SPAN_COUNT)
            adapter = searchGridAdapter
        }

        rxEvent(detailSimilarViewModel)
        liveDataObserver(detailSimilarViewModel)

        return view
    }

    private fun rxEvent(detailSimilarViewModel: DetailSimilarViewModel) {

        val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(20)
            .setPageSize(20)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(true)
            .build()

        val rxBuilder = RxPagedListBuilder(object : DataSource.Factory<Int, MovieMainResponse.Movie>() {
            override fun create(): DataSource<Int, MovieMainResponse.Movie> {
                return detailSimilarViewModel.getDataSource(movieId)
            }
        }, config)
        detailSimilarViewModel.addDisposable(rxBuilder.buildObservable().subscribe(searchGridAdapter::submitList))
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