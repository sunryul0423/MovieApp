package com.movie.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.movie.R
import com.movie.common.constants.DETAIL_CREDIT_CAST
import com.movie.databinding.FragmentDetailSimilarBinding
import com.movie.model.data.CreditResponse
import com.movie.model.view.MovieDetailCreditViewModel
import com.movie.model.view.MovieDetailCreditViewModelFactory
import org.koin.android.ext.android.inject


class MovieDetailCreditCastFragment : BaseFragment<FragmentDetailSimilarBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_detail_similar

    private var castList: List<CreditResponse.Cast> = emptyList()

    private val movieDetailCreditViewModelFactory: MovieDetailCreditViewModelFactory by inject()

    companion object {
        fun newInstance(creditResponse: CreditResponse): MovieDetailCreditCastFragment {
            val fragment = MovieDetailCreditCastFragment()
            val arg = Bundle()
            arg.putSerializable(DETAIL_CREDIT_CAST, creditResponse)
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
        castList = (arguments?.getSerializable(DETAIL_CREDIT_CAST) as CreditResponse).cast
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        val movieDetailCreditViewModel = ViewModelProviders.of(this, movieDetailCreditViewModelFactory)
            .get(MovieDetailCreditViewModel::class.java)
        movieDetailCreditViewModel.setCastList(castList)
        viewBinding.movieDetailCreditViewModel = movieDetailCreditViewModel
        viewBinding.lifecycleOwner = this
        return view
    }

//    fun setView() {
//        val creaditInfoList: MutableList<CreaditInfoModel> = mutableListOf()
//        for (i in castList.indices) {
//            val creaditInfoModel = CreaditInfoModel(castList[i].profilePath, castList[i].name, castList[i].character)
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