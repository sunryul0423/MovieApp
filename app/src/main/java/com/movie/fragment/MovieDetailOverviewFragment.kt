package com.movie.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movie.R
import com.movie.`interface`.IRxResult
import com.movie.`interface`.IScrollChangeListener
import com.movie.activity.DetailCreditActivity
import com.movie.activity.DetailMovieActivity
import com.movie.common.constants.*
import com.movie.common.utils.CommonUtil
import com.movie.customview.adapter.CreditListAdapter
import com.movie.customview.adapter.RecyclerViewDecoration
import com.movie.customview.adapter.VideoListAdapter
import com.movie.customview.view.CoustomScrollView
import com.movie.model.data.*

class MovieDetailOverviewFragment : BaseFragment(), View.OnClickListener {

    private lateinit var movieDetailResponse: MovieDetailResponse
    private lateinit var csView: CoustomScrollView
    private lateinit var tvOverviewMore: TextView
    private lateinit var tvOverviewStory: TextView
    private lateinit var rvCredit: RecyclerView
    private lateinit var rvVideo: RecyclerView

    private var movieId: Int = 0
    private lateinit var creditResponse: CreditResponse

    private lateinit var scrollChangeListener: IScrollChangeListener

    companion object {
        fun newInstance(movieDetailResponse: MovieDetailResponse, movieId: Int): MovieDetailOverviewFragment {
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
        movieDetailResponse = arguments?.getSerializable(MOVIE_DETAIL_RESPONSE_KEY) as MovieDetailResponse
        movieId = arguments?.getInt(MOVIE_ID) ?: 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail_overview, container, false)
        setView(view)
        requestApi()
        return view
    }

    fun setView(view: View) {
        csView = view.findViewById(R.id.cs_view)
        csView.isNestedScrollingEnabled = true
        csView.requestFocus()
        csView.setOnScrollChangeListener { _, _, _, _, _ ->
            if (!csView.canScrollVertically(-1)) { //최상단 스크롤
                scrollChangeListener.onScroll(true)
            }
        }
        tvOverviewMore = view.findViewById(R.id.tv_overview_more)
        tvOverviewMore.setOnClickListener(this)
        tvOverviewStory = view.findViewById(R.id.tv_overview_story)
        val tvCreditAll: TextView = view.findViewById(R.id.tv_credit_all)
        tvCreditAll.setOnClickListener(this)
        rvCredit = view.findViewById(R.id.rv_credit)
        rvVideo = view.findViewById(R.id.rv_video)

        val vDetailReleaseDate: LinearLayout = view.findViewById(R.id.v_detail_release_date)
        val vDetailSpokenLanguages: LinearLayout = view.findViewById(R.id.v_detail_spoken_languages)
        val vDetailRunTime: LinearLayout = view.findViewById(R.id.v_detail_run_time)
        val vDetailProductionCountry: LinearLayout = view.findViewById(R.id.v_detail_production_countries)
        val vDetailProductionCompany: LinearLayout = view.findViewById(R.id.v_detail_production_companies)

        val tvDetailReleaseDateTitle: TextView = vDetailReleaseDate.findViewById(R.id.tv_detail_info_title)
        val tvDetailReleaseDateValue: TextView = vDetailReleaseDate.findViewById(R.id.tv_detail_info_value)
        val tvDetailSpokenLanguagesTitle: TextView = vDetailSpokenLanguages.findViewById(R.id.tv_detail_info_title)
        val tvDetailSpokenLanguagesValue: TextView = vDetailSpokenLanguages.findViewById(R.id.tv_detail_info_value)
        val tvDetailRunTimeTitle: TextView = vDetailRunTime.findViewById(R.id.tv_detail_info_title)
        val tvDetailRunTimeValue: TextView = vDetailRunTime.findViewById(R.id.tv_detail_info_value)
        val tvDetailProductionCountryTitle: TextView = vDetailProductionCountry.findViewById(R.id.tv_detail_info_title)
        val tvDetailProductionCountryValue: TextView = vDetailProductionCountry.findViewById(R.id.tv_detail_info_value)
        val tvDetailProductionCompanyTitle: TextView = vDetailProductionCompany.findViewById(R.id.tv_detail_info_title)
        val tvDetailProductionCompanyValue: TextView = vDetailProductionCompany.findViewById(R.id.tv_detail_info_value)

        tvOverviewStory.text = movieDetailResponse.overview

        tvDetailReleaseDateTitle.text = resources.getString(R.string.detail_info_release_date)
        tvDetailReleaseDateValue.text = movieDetailResponse.releaseDate

        tvDetailSpokenLanguagesTitle.text = resources.getString(R.string.detail_info_spoken_languages)
        if (movieDetailResponse.spokenLanguages.firstOrNull() != null) {
            val str = StringBuilder()
            for (i in 0 until movieDetailResponse.spokenLanguages.size) {
                tvDetailSpokenLanguagesValue.text = str.append(movieDetailResponse.spokenLanguages[i].name)
                if (movieDetailResponse.spokenLanguages.size != 1 && i != movieDetailResponse.spokenLanguages.size - 1) {
                    str.append(", ")
                }
            }
        }

        tvDetailRunTimeTitle.text = resources.getString(R.string.detail_info_run_time)
        tvDetailRunTimeValue.text = "${movieDetailResponse.runTime}"

        tvDetailProductionCountryTitle.text = resources.getString(R.string.detail_info_production_country)
        if (movieDetailResponse.productionCountries.firstOrNull() != null) {
            val str = StringBuilder()
            for (i in 0 until movieDetailResponse.productionCountries.size) {
                tvDetailProductionCountryValue.text = str.append(movieDetailResponse.productionCountries[i].name)
                if (movieDetailResponse.productionCountries.size != 1 && i != movieDetailResponse.productionCountries.size - 1) {
                    str.append("\n")
                }
            }
        }

        tvDetailProductionCompanyTitle.text = resources.getString(R.string.detail_info_production_company)
        if (movieDetailResponse.productionCompanies.firstOrNull() != null) {
            val str = StringBuilder()
            for (i in 0 until movieDetailResponse.productionCompanies.size) {
                tvDetailProductionCompanyValue.text = str.append(movieDetailResponse.productionCompanies[i].name)
                if (movieDetailResponse.productionCompanies.size != 1 && i != movieDetailResponse.productionCompanies.size - 1) {
                    str.append("\n")
                }
            }
        }
    }

    private fun requestApi() {
        rxResponseManager.add(
            apiRequest.getVideos(
                movieId,
                hashMapOf(API_KEY to TMDB_API_KEY)
            ), object : IRxResult {

                override fun <T> onNext(response: T) {
                    val videoList: List<VideoResponse.Videos> = (response as VideoResponse).results
                        ?: emptyList()

                    val videoListAdapter = VideoListAdapter(mContext, videoList)
                    rvVideo.setHasFixedSize(true)
                    val spacing: Int = resources.getDimensionPixelSize(R.dimen.detail_overview_credit_divider)
                    val recyclerViewDecoration = RecyclerViewDecoration(
                        false,
                        videoList.size,
                        RecyclerViewSpacing(spacing, spacing, spacing, spacing)
                    )
                    rvVideo.addItemDecoration(recyclerViewDecoration)
                    val layoutManager = LinearLayoutManager(mContext)
                    layoutManager.orientation = LinearLayoutManager.HORIZONTAL
                    rvVideo.layoutManager = layoutManager
                    rvVideo.adapter = videoListAdapter
                }

                override fun onErrer(error: Throwable) {
                }
            })

        rxResponseManager.add(apiRequest.getCredit(movieId, CommonUtil.getParam()), object : IRxResult {

            override fun <T> onNext(response: T) {
                creditResponse = response as CreditResponse
                var castList = creditResponse.cast
                val crewList = creditResponse.crew

                val creaditInfoList: MutableList<CreaditInfoModel> = mutableListOf()
                for (i in crewList.indices) {
                    if ("Director" == crewList[i].job) {
                        val creaditInfoModel =
                            CreaditInfoModel(crewList[i].profilePath, crewList[i].name, crewList[i].job)
                        creaditInfoList.add(creaditInfoModel)
                        break
                    }
                }

                if (CREDIT_COUNT < castList.size) {
                    castList = castList.subList(0, CREDIT_COUNT)
                }
                for (i in castList.indices) {
                    val creaditInfoModel =
                        CreaditInfoModel(castList[i].profilePath, castList[i].name, castList[i].character)
                    creaditInfoList.add(creaditInfoModel)
                }

                val creditListAdapter = CreditListAdapter(mContext, creaditInfoList)
                rvCredit.setHasFixedSize(true)
                val spacing: Int = resources.getDimensionPixelSize(R.dimen.detail_overview_credit_divider)
                val recyclerViewDecoration = RecyclerViewDecoration(
                    false,
                    creaditInfoList.size,
                    RecyclerViewSpacing(spacing, spacing, spacing, spacing)
                )
                rvCredit.addItemDecoration(recyclerViewDecoration)
                val layoutManager = LinearLayoutManager(mContext)
                layoutManager.orientation = LinearLayoutManager.HORIZONTAL
                rvCredit.layoutManager = layoutManager
                rvCredit.adapter = creditListAdapter
            }

            override fun onErrer(error: Throwable) {
            }
        })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_overview_more -> {
                if (tvOverviewStory.visibility == View.VISIBLE) {
                    tvOverviewStory.visibility = View.GONE
                    tvOverviewMore.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        null,
                        ContextCompat.getDrawable(mContext, R.drawable.baseline_keyboard_arrow_up_black),
                        null
                    )
                } else {
                    tvOverviewStory.visibility = View.VISIBLE
                    tvOverviewMore.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        null,
                        ContextCompat.getDrawable(mContext, R.drawable.baseline_keyboard_arrow_down_black),
                        null
                    )
                }
            }

            R.id.tv_credit_all -> {
                val intent = Intent(mContext, DetailCreditActivity::class.java)
                intent.putExtra(MOVIE_NAME, movieDetailResponse.title)
                intent.putExtra(DETAIL_CREDIT, creditResponse)
                ActivityCompat.startActivity(mContext, intent, null)
            }
        }
    }
}