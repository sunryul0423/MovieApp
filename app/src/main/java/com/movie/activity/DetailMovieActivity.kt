package com.movie.activity

import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.movie.R
import com.movie.common.constants.MOVIE_ID
import com.movie.databinding.ActivityDetailMovieBinding
import com.movie.fragment.MovieDetailOverviewFragment
import com.movie.fragment.MovieDetailSimilarFragment
import com.movie.interfaces.IScrollChangeListener
import com.movie.interfaces.OnScrollListener
import com.movie.model.data.MovieDetailResponse
import com.movie.model.view.DetailMovieViewModel
import com.movie.model.view.DetailMovieViewModelFactory

class DetailMovieActivity : BaseActivity<ActivityDetailMovieBinding>(), IScrollChangeListener {
    override val layoutResourceId: Int
        get() = R.layout.activity_detail_movie

    override fun onScroll(enableScroll: Boolean) {
        viewBinding.csView.isEnableScroll(enableScroll)
    }

    private lateinit var detailMovieViewModelFactory: DetailMovieViewModelFactory

    private var movieId: Int = 0

    private lateinit var tvMenuName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActionBar(true, "")
        movieId = intent.getIntExtra(MOVIE_ID, 0)

        detailMovieViewModelFactory = DetailMovieViewModelFactory(apiRequest, movieId)
        val detailMovieViewModel =
            ViewModelProviders.of(this, detailMovieViewModelFactory).get(DetailMovieViewModel::class.java)
        viewBinding.detailMovieViewModel = detailMovieViewModel
        viewBinding.lifecycleOwner = this

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
    }

    private fun setView(movieDetailResponse: MovieDetailResponse) {
        val ivMenuLeft: ImageView = coustomActionBar.findViewById(R.id.iv_menu_left)
        tvMenuName = coustomActionBar.findViewById(R.id.tv_menu_name)
        val ivMenuRight: ImageView = coustomActionBar.findViewById(R.id.iv_menu_right)
        ivMenuLeft.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.common_text_white))
        tvMenuName.setTextColor(ContextCompat.getColor(mContext, R.color.common_text_white))
        tvMenuName.alpha = 0f
        ivMenuRight.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.common_text_white))

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
                    tvMenuName.alpha = tmpAlpha
                }
            }
        })
    }
}