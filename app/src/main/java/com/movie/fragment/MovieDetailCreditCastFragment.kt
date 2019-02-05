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


class MovieDetailCreditCastFragment : BaseFragment() {

    private var castList: List<CreditResponse.Cast> = emptyList()
    private lateinit var rvSimilar: RecyclerView

    companion object {
        fun newInstance(creditResponse: CreditResponse): MovieDetailCreditCastFragment {
            val fragment = MovieDetailCreditCastFragment()
            val arg = Bundle()
            arg.putSerializable(MovieConstant.DETAIL_CREDIT_CAST, creditResponse)
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
        castList = (arguments?.getSerializable(MovieConstant.DETAIL_CREDIT_CAST) as CreditResponse).cast
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_detail_similar, container, false)
        setView(view)
        return view
    }

    fun setView(view: View) {
        rvSimilar = view.findViewById(R.id.rv_similar)

        val creaditInfoList: MutableList<CreaditInfoModel> = mutableListOf()
        for (i in castList.indices) {
            val creaditInfoModel = CreaditInfoModel()
            creaditInfoModel.url = castList[i].profilePath
            creaditInfoModel.name = castList[i].name
            creaditInfoModel.part = castList[i].character
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