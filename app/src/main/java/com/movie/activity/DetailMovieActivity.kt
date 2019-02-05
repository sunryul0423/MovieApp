package com.movie.activity

import android.content.res.ColorStateList
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.movie.R
import com.movie.`interface`.IRxResult
import com.movie.`interface`.IScrollChangeListener
import com.movie.`interface`.OnScrollListener
import com.movie.common.constants.MovieConstant
import com.movie.common.utils.CommonUtil
import com.movie.customview.view.CoustomScrollView
import com.movie.fragment.MovieDetailOverviewFragment
import com.movie.fragment.MovieDetailSimilarFragment
import com.movie.model.data.MovieDetailResponse

class DetailMovieActivity : BaseActivity(), View.OnClickListener, IScrollChangeListener {
    override fun onScroll(enableScroll: Boolean) {
        csView.isEnableScroll(enableScroll)
    }

    private var movieId: Int = 0
    private var rgPosition: Int = 0

    private lateinit var movieDetailResponse: MovieDetailResponse

    lateinit var csView: CoustomScrollView
    private lateinit var tvMenuName: TextView
    private lateinit var ivBackDrop: ImageView
    private lateinit var ivDetailMoviePoster: ImageView
    private lateinit var tvDetailTitle: TextView
    private lateinit var tvDetailOriginalTitle: TextView
    private lateinit var tvDetailVoteAverage: TextView
    private lateinit var tvDetailGenres: TextView
    private lateinit var tvBtnDetailVideo: TextView
    private lateinit var rgDetailBtn: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        initActionBar(true, "")
        movieId = intent.getIntExtra(MovieConstant.MOVIE_ID, 0)
        setView()
        reqeustApi()
    }

    private fun setView() {
        val ivMenuLeft: ImageView = coustomActionBar.findViewById(R.id.iv_menu_left)
        tvMenuName = coustomActionBar.findViewById(R.id.tv_menu_name)
        val ivMenuRight: ImageView = coustomActionBar.findViewById(R.id.iv_menu_right)
        ivMenuLeft.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.common_text_white))
        tvMenuName.setTextColor(ContextCompat.getColor(mContext, R.color.common_text_white))
        tvMenuName.alpha = 0f
        ivMenuRight.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.common_text_white))

        tvDetailTitle = findViewById(R.id.tv_detail_title)
        tvDetailOriginalTitle = findViewById(R.id.tv_detail_original_title)
        tvDetailVoteAverage = findViewById(R.id.tv_detail_vote_average)
        tvDetailGenres = findViewById(R.id.tv_detail_genres)
        tvBtnDetailVideo = findViewById(R.id.tv_btn_detail_video)
        tvBtnDetailVideo.setOnClickListener(this)
        rgDetailBtn = findViewById(R.id.rg_detail_btn)
        rgDetailBtn.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_detail_overview -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fl_contents, MovieDetailOverviewFragment.newInstance(movieDetailResponse, movieId)).commitAllowingStateLoss()
                }

                R.id.rb_detail_similar -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fl_contents, MovieDetailSimilarFragment.newInstance(movieId)).commitAllowingStateLoss()
                }
            }
        }
//        rgDetailBtn.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
//            override fun onGlobalLayout() {
//                rgDetailBtn.viewTreeObserver.removeOnGlobalLayoutListener(this)
//                rgPosition = rgDetailBtn.y.toInt()
//            }
//        })

        ivBackDrop = findViewById(R.id.iv_back_drop)
        ivDetailMoviePoster = findViewById(R.id.iv_detail_movie_poster)
        csView = findViewById(R.id.cs_view)
        csView.setOnScrollListener(object : OnScrollListener {
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
//                    if (scrollY >= rgPosition) {
//                        csView.scrollY = rgPosition
//                    } else {
//                    }
                    ivBackDrop.alpha = 1 - tmpAlpha //백그림
                    tvMenuName.alpha = tmpAlpha

                }
            }
        })
    }


    private fun reqeustApi() {

        rxResponseManager.add(apiRequest.getDetail(movieId, CommonUtil.getParam()), object : IRxResult {

            override fun <T> onNext(response: T) {
                movieDetailResponse = response as MovieDetailResponse
                setActionBarTitle(movieDetailResponse.title)
                supportFragmentManager.beginTransaction().replace(R.id.fl_contents, MovieDetailOverviewFragment.newInstance(movieDetailResponse, movieId)).commitAllowingStateLoss()

                Glide.with(mContext)
                        .load(MovieConstant.IMAGE_URL + movieDetailResponse.backdropPath)
                        .override(mContext.resources.displayMetrics.widthPixels, mContext.resources.displayMetrics.widthPixels / 3)
                        .error(R.drawable.film_poster_placeholder)
                        .placeholder(R.drawable.film_poster_placeholder)
                        .into(ivBackDrop)
                Glide.with(mContext)
                        .load(MovieConstant.IMAGE_URL + movieDetailResponse.posterPath)
                        .override(mContext.resources.displayMetrics.widthPixels, mContext.resources.displayMetrics.widthPixels / 3)
                        .error(R.drawable.film_poster_placeholder)
                        .placeholder(R.drawable.film_poster_placeholder)
                        .into(ivDetailMoviePoster)

                tvDetailTitle.text = movieDetailResponse.title
                tvDetailOriginalTitle.text = movieDetailResponse.originalTitle
                tvDetailVoteAverage.text = "${movieDetailResponse.voteAverage}"
                if (movieDetailResponse.genres?.firstOrNull() != null) {
                    val str = StringBuilder()
                    for (i in 0 until movieDetailResponse.genres!!.size) {
                        tvDetailGenres.text = str.append(movieDetailResponse.genres!![i].name)
                        if (movieDetailResponse.genres!!.size != 1 && i != movieDetailResponse.genres!!.size - 1) {
                            str.append(", ")
                        }
                    }
                }
            }

            override fun onErrer(error: Throwable) {
            }
        })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_btn_detail_video -> {
                //좋아요
            }
        }
    }

}