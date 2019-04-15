package com.movie.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.movie.R
import com.movie.common.constants.DETAIL_CREDIT_CREW
import com.movie.databinding.FragmentDetailSimilarBinding
import com.movie.model.data.CreditResponse
import com.movie.model.view.MovieDetailCreditViewModel
import com.movie.model.view.MovieDetailCreditViewModelFactory
import org.koin.android.ext.android.inject

class MovieDetailCreditCrewFragment : BaseFragment<FragmentDetailSimilarBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_detail_similar

    var crewList: List<CreditResponse.Crew> = emptyList()

    private val movieDetailCreditViewModelFactory: MovieDetailCreditViewModelFactory by inject()

    companion object {
        fun newInstance(creditResponse: CreditResponse): MovieDetailCreditCrewFragment {
            val fragment = MovieDetailCreditCrewFragment()
            val arg = Bundle()
            arg.putSerializable(DETAIL_CREDIT_CREW, creditResponse)
            fragment.arguments = arg
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        crewList = (arguments?.getSerializable(DETAIL_CREDIT_CREW) as CreditResponse).crew
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        val movieDetailCreditViewModel = ViewModelProviders.of(this, movieDetailCreditViewModelFactory)
            .get(MovieDetailCreditViewModel::class.java)
        movieDetailCreditViewModel.setCrewList(crewList)
        viewBinding.movieDetailCreditViewModel = movieDetailCreditViewModel
        viewBinding.lifecycleOwner = this

        return view
    }

//    fun setView(view: View) {
//        val creaditInfoList: MutableList<CreaditInfoModel> = mutableListOf()
//        for (i in crewList.indices) {
//            val creaditInfoModel = CreaditInfoModel(crewList[i].profilePath, crewList[i].name, crewList[i].job)
//            creaditInfoList.add(creaditInfoModel)
//        }
//        val creditListAdapter = CreditListAdapter(mContext, creaditInfoList)
//        rvSimilar.setHasFixedSize(true)
//        val spacing: Int = resources.getDimensionPixelSize(R.dimen.detail_overview_credit_divider)
//        val recyclerViewDecoration =
//            RecyclerViewDecoration(true, 3, RecyclerViewSpacing(spacing, spacing, spacing, spacing))
//        rvSimilar.addItemDecoration(recyclerViewDecoration)
//        rvSimilar.layoutManager = GridLayoutManager(mContext, 3)
//        rvSimilar.adapter = creditListAdapter
//    }
}