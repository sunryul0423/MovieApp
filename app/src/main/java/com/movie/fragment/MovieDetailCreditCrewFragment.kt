package com.movie.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.movie.R
import com.movie.common.DETAIL_CREDIT_CREW
import com.movie.databinding.FragmentDetailCreditCrewBinding
import com.movie.model.data.CreditResponse
import com.movie.model.view.DetailCreditCastViewModel
import com.movie.model.view.DetailCreditCastViewModelFactory
import org.koin.android.ext.android.inject

class MovieDetailCreditCrewFragment : BaseFragment<FragmentDetailCreditCrewBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_detail_credit_crew

    private var crewList: List<CreditResponse.Crew> = emptyList()

    private val detailCreditCastViewModelFactory: DetailCreditCastViewModelFactory by inject()

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
        val detailCreditCastViewModel = ViewModelProvider(this, detailCreditCastViewModelFactory)
            .get(DetailCreditCastViewModel::class.java)
        detailCreditCastViewModel.setCrewList(crewList)
        viewBinding.detailCreditCastViewModel = detailCreditCastViewModel
        viewBinding.lifecycleOwner = this
        return view
    }
}