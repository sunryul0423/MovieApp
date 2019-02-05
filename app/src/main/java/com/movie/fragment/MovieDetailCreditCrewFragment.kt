package com.movie.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.movie.R
import com.movie.common.constants.MovieConstant
import com.movie.customview.adapter.CreditListAdapter
import com.movie.customview.adapter.RecyclerViewDecoration
import com.movie.model.data.CreaditInfoModel
import com.movie.model.data.CreditResponse
import com.movie.model.data.RecyclerViewSpacing

class MovieDetailCreditCrewFragment : BaseFragment() {

    var crewList: List<CreditResponse.Crew> = emptyList()
    private lateinit var rvSimilar: RecyclerView

    companion object {
        fun newInstance(creditResponse: CreditResponse): MovieDetailCreditCrewFragment {
            val fragment = MovieDetailCreditCrewFragment()
            val arg = Bundle()
            arg.putSerializable(MovieConstant.DETAIL_CREDIT_CREW, creditResponse)
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
        crewList = (arguments?.getSerializable(MovieConstant.DETAIL_CREDIT_CREW) as CreditResponse).crew
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_detail_similar, container, false)
        setView(view)
        return view
    }

    fun setView(view: View) {
        rvSimilar = view.findViewById(R.id.rv_similar)

        val creaditInfoList: MutableList<CreaditInfoModel> = mutableListOf()
        for (i in crewList.indices) {
            val creaditInfoModel = CreaditInfoModel()
            creaditInfoModel.url = crewList[i].profilePath
            creaditInfoModel.name = crewList[i].name
            creaditInfoModel.part = crewList[i].job
            creaditInfoList.add(creaditInfoModel)
        }
        val creditListAdapter = CreditListAdapter(mContext, creaditInfoList)
        rvSimilar.setHasFixedSize(true)
        val spacing: Int = resources.getDimensionPixelSize(R.dimen.detail_overview_credit_divider)
        val recyclerViewDecoration = RecyclerViewDecoration(true, 3, RecyclerViewSpacing(spacing, spacing, spacing, spacing))
        rvSimilar.addItemDecoration(recyclerViewDecoration)
        rvSimilar.layoutManager = GridLayoutManager(mContext, 3)
        rvSimilar.adapter = creditListAdapter
    }
}