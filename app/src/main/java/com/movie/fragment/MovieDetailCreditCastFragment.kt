package com.movie.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.movie.R
import com.movie.common.constants.DETAIL_CREDIT_CAST
import com.movie.databinding.FragmentDetailCreditCastBinding
import com.movie.model.data.CreditResponse
import com.movie.model.view.DetailCreditCastViewModel
import com.movie.model.view.DetailCreditCastViewModelFactory
import org.koin.android.ext.android.inject


class MovieDetailCreditCastFragment : BaseFragment<FragmentDetailCreditCastBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_detail_credit_cast

    private var castList: List<CreditResponse.Cast> = emptyList()

    private val detailCreditCastViewModelFactory: DetailCreditCastViewModelFactory by inject()

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
        val detailCreditCastViewModel = ViewModelProviders.of(this, detailCreditCastViewModelFactory)
            .get(DetailCreditCastViewModel::class.java)
        detailCreditCastViewModel.setCastList(castList)
        viewBinding.detailCreditCastViewModel = detailCreditCastViewModel
        viewBinding.lifecycleOwner = this
        return view
    }
}