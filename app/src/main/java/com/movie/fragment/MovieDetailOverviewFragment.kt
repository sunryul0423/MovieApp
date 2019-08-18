package com.movie.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jakewharton.rxbinding2.view.RxView
import com.movie.R
import com.movie.activity.DetailCreditActivity
import com.movie.activity.DetailMovieActivity
import com.movie.common.*
import com.movie.databinding.FragmentDetailOverviewBinding
import com.movie.interfaces.IScrollChangeListener
import com.movie.model.data.MovieDetailResponse
import com.movie.model.view.DetailOverviewViewModel
import com.movie.model.view.DetailOverviewViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class MovieDetailOverviewFragment : BaseFragment<FragmentDetailOverviewBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_detail_overview


    private val detailOverviewViewModelFactory: DetailOverviewViewModelFactory by inject()

    private lateinit var movieDetailResponse: MovieDetailResponse
    private var movieId: Int = 0
    private lateinit var scrollChangeListener: IScrollChangeListener

    companion object {
        fun newInstance(movieDetailResponse: MovieDetailResponse?, movieId: Int): MovieDetailOverviewFragment {
            val fragment = MovieDetailOverviewFragment()
            val args = Bundle()
            args.putSerializable(MOVIE_DETAIL_RESPONSE_KEY, movieDetailResponse)
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
        movieDetailResponse = (arguments?.getSerializable(MOVIE_DETAIL_RESPONSE_KEY) as MovieDetailResponse)
        movieId = arguments?.getInt(MOVIE_ID) ?: 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        val detailOverviewViewModel =
            ViewModelProvider(this, detailOverviewViewModelFactory).get(DetailOverviewViewModel::class.java).apply {
                this.setVisibleMore(true)
                this.requestOverviewApi(movieId, movieDetailResponse.overview)
            }
        viewBinding.detailOverviewViewModel = detailOverviewViewModel
        viewBinding.lifecycleOwner = this

        rxEvent(detailOverviewViewModel)
        liveDataObserver(detailOverviewViewModel)
        setView()
        return view
    }

    private fun liveDataObserver(detailOverviewViewModel: DetailOverviewViewModel) {
        detailOverviewViewModel.getProgress().observe(this, Observer {
            if (it)
                progress.show()
            else
                progress.cancel()
        })
        detailOverviewViewModel.getThrowableData().observe(this, Observer {
            showThrowableToast(mContext, it)
        })
    }

    private fun rxEvent(detailOverviewViewModel: DetailOverviewViewModel) {
        detailOverviewViewModel.addDisposable(
            RxView.clicks(viewBinding.tvCreditAll)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe {
                    val intent = Intent(mContext, DetailCreditActivity::class.java)
                    intent.putExtra(MOVIE_NAME, movieDetailResponse.title)
                    intent.putExtra(DETAIL_CREDIT, viewBinding.detailOverviewViewModel?.getCreditResponse()?.value)
                    ActivityCompat.startActivity(mContext, intent, null)
                }
        )
    }

    private fun setView() {
        viewBinding.csView.requestFocus()
        viewBinding.csView.isNestedScrollingEnabled = true
        viewBinding.csView.setOnScrollChangeListener { _, _, _, _, _ ->
            if (!viewBinding.csView.canScrollVertically(-1)) { //최상단 스크롤
                scrollChangeListener.onScroll(true)
            }
        }

        viewBinding.vDetailReleaseDate.tvDetailInfoTitle.text = resources.getString(R.string.detail_info_release_date)
        viewBinding.vDetailReleaseDate.tvDetailInfoValue.text = movieDetailResponse.releaseDate

        viewBinding.vDetailSpokenLanguages.tvDetailInfoTitle.text =
            resources.getString(R.string.detail_info_spoken_languages)
        if (movieDetailResponse.spokenLanguages.firstOrNull() != null) {
            val str = StringBuilder()
            for (i in 0 until movieDetailResponse.spokenLanguages.size) {
                viewBinding.vDetailSpokenLanguages.tvDetailInfoValue.text =
                    str.append(movieDetailResponse.spokenLanguages[i].name)
                if (movieDetailResponse.spokenLanguages.size != 1 && i != movieDetailResponse.spokenLanguages.size - 1) {
                    str.append(", ")
                }
            }
        }

        viewBinding.vDetailRunTime.tvDetailInfoTitle.text = resources.getString(R.string.detail_info_run_time)
        viewBinding.vDetailRunTime.tvDetailInfoValue.text = "${movieDetailResponse.runTime}"

        viewBinding.vDetailProductionCountries.tvDetailInfoTitle.text =
            resources.getString(R.string.detail_info_production_country)
        if (movieDetailResponse.productionCountries.firstOrNull() != null) {
            val str = StringBuilder()
            for (i in 0 until movieDetailResponse.productionCountries.size) {
                viewBinding.vDetailProductionCountries.tvDetailInfoValue.text =
                    str.append(movieDetailResponse.productionCountries[i].name)
                if (movieDetailResponse.productionCountries.size != 1 && i != movieDetailResponse.productionCountries.size - 1) {
                    str.append("\n")
                }
            }
        }

        viewBinding.vDetailProductionCompanies.tvDetailInfoTitle.text =
            resources.getString(R.string.detail_info_production_company)
        if (movieDetailResponse.productionCompanies.firstOrNull() != null) {
            val str = StringBuilder()
            for (i in 0 until movieDetailResponse.productionCompanies.size) {
                viewBinding.vDetailProductionCompanies.tvDetailInfoValue.text =
                    str.append(movieDetailResponse.productionCompanies[i].name)
                if (movieDetailResponse.productionCompanies.size != 1 && i != movieDetailResponse.productionCompanies.size - 1) {
                    str.append("\n")
                }
            }
        }
    }
}