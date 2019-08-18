package com.movie.activity

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.movie.R
import com.movie.common.MOVIE_ID
import com.movie.common.showThrowableToast
import com.movie.databinding.ActivityDetailMovieBinding
import com.movie.fragment.MovieDetailOverviewFragment
import com.movie.fragment.MovieDetailSimilarFragment
import com.movie.interfaces.IScrollChangeListener
import com.movie.interfaces.OnScrollListener
import com.movie.model.data.MovieDetailResponse
import com.movie.model.view.DetailMovieViewModel
import com.movie.model.view.DetailMovieViewModelFactory
import kotlinx.android.synthetic.main.view_action_bar.view.*
import org.koin.android.ext.android.inject

class DetailMovieActivity : BaseActivity<ActivityDetailMovieBinding>(), IScrollChangeListener {
    override val layoutResourceId: Int
        get() = R.layout.activity_detail_movie

    override fun onScroll(enableScroll: Boolean) {
        viewBinding.csView.isEnableScroll(enableScroll)
    }

    private val detailMovieViewModelFactory: DetailMovieViewModelFactory by inject()
    private var movieId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActionBar(true, "")
        movieId = intent.getIntExtra(MOVIE_ID, 0)

        val detailMovieViewModel =
            ViewModelProvider(this, detailMovieViewModelFactory).get(DetailMovieViewModel::class.java).apply {
                this.requestDetailApi(movieId)
            }

        viewBinding.detailMovieViewModel = detailMovieViewModel
        viewBinding.lifecycleOwner = this
        liveDataObserver(detailMovieViewModel)
    }

    private fun liveDataObserver(detailMovieViewModel: DetailMovieViewModel) {
        detailMovieViewModel.getProgress().observe(this, Observer {
            if (it)
                progress.show()
            else
                progress.cancel()
        })

        detailMovieViewModel.getThrowableData().observe(this, Observer {
            showThrowableToast(this, it)
        })

        detailMovieViewModel.movieDetailResponse.observe(this,
            Observer<MovieDetailResponse> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.fl_contents,
                    MovieDetailOverviewFragment.newInstance(
                        it,
                        movieId
                    )
                ).commitAllowingStateLoss()
                setView(it)
            })
        detailMovieViewModel.title.observe(this, Observer {
            initActionBar(true, it)
        })
    }

    private fun setView(movieDetailResponse: MovieDetailResponse) {
        viewBinding.cvActionbar.iv_menu_left.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
        viewBinding.cvActionbar.tv_menu_name.run {
            this.setTextColor(ContextCompat.getColor(this@DetailMovieActivity, R.color.white))
            this.alpha = 0f
        }
        viewBinding.cvActionbar.iv_menu_right.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))

        viewBinding.rgDetailBtn.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_detail_overview -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.fl_contents,
                        MovieDetailOverviewFragment.newInstance(
                            movieDetailResponse,
                            movieId
                        )
                    ).commitAllowingStateLoss()
                }

                R.id.rb_detail_similar -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fl_contents, MovieDetailSimilarFragment.newInstance(movieId))
                        .commitAllowingStateLoss()
                }
            }
        }

        viewBinding.csView.setOnScrollListener(object : OnScrollListener {
            override fun onScroll(scrollY: Int) {
                val detailTopHeight = resources.getDimension(R.dimen.detail_back_drop_padding)
                val tmpAlpha: Float = if (scrollY < detailTopHeight) {
                    if (scrollY / detailTopHeight < 0.2f) {
                        0f
                    } else {
                        scrollY / detailTopHeight
                    }
                } else {
                    1f
                }

                runOnUiThread {
                    viewBinding.ivBackDrop.alpha = 1 - tmpAlpha //백그림
                    customActionBar.tv_menu_name.alpha = tmpAlpha
                }
            }
        })
    }
}