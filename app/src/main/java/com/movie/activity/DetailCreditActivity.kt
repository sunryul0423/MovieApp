package com.movie.activity

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.movie.R
import com.movie.util.DETAIL_CREDIT
import com.movie.util.MOVIE_NAME
import com.movie.databinding.ActivityDetailCreditBinding
import com.movie.fragment.MovieDetailCreditCastFragment
import com.movie.fragment.MovieDetailCreditCrewFragment
import com.movie.model.data.CreditResponse
import com.movie.model.view.DetailCreditModel
import kotlinx.android.synthetic.main.view_action_bar.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailCreditActivity : BaseActivity<ActivityDetailCreditBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_detail_credit

    private lateinit var creditResponse: CreditResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActionBar(true, intent.getStringExtra(MOVIE_NAME))

        creditResponse = intent.getSerializableExtra(DETAIL_CREDIT) as CreditResponse
        val detailCreditModel: DetailCreditModel by viewModel()
        detailCreditModel.setCreditResponse(creditResponse)
        viewBinding.run {
            this.detailCreditModel = detailCreditModel
            this.lifecycleOwner = this@DetailCreditActivity
        }

        detailCreditModel.getCreditResponse().observe(this, Observer {
            viewBinding.rgDetailCreditBtn.check(R.id.rb_detail_cast)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fl_contents, MovieDetailCreditCastFragment.newInstance(creditResponse))
                .commitAllowingStateLoss()
        })

        setView()
    }

    private fun setView() {

        customActionBar.iv_menu_left.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
        customActionBar.tv_menu_name.setTextColor(ContextCompat.getColor(this, R.color.white))
        customActionBar.iv_menu_right.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))

        viewBinding.rgDetailCreditBtn.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_detail_cast -> {
                    supportFragmentManager.beginTransaction()
                        .commitAllowingStateLoss()
                }

                R.id.rb_detail_crew -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fl_contents, MovieDetailCreditCrewFragment.newInstance(creditResponse))
                        .commitAllowingStateLoss()
                }
            }
        }
    }
}