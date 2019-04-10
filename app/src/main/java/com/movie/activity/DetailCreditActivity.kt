package com.movie.activity

import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.movie.R
import com.movie.common.constants.DETAIL_CREDIT
import com.movie.common.constants.MOVIE_NAME
import com.movie.databinding.ActivityDetailCreditBinding
import com.movie.fragment.MovieDetailCreditCastFragment
import com.movie.fragment.MovieDetailCreditCrewFragment
import com.movie.model.data.CreditResponse
import com.movie.model.view.DetailCreditModel
import com.movie.model.view.DetailCreditModelFactory
import org.koin.android.ext.android.inject

class DetailCreditActivity : BaseActivity<ActivityDetailCreditBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_detail_credit

    private val detailCreditModelFactory: DetailCreditModelFactory by inject()

    private lateinit var creditResponse: CreditResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActionBar(true, intent.getStringExtra(MOVIE_NAME))

        val detailCreditModel = ViewModelProviders.of(this, detailCreditModelFactory).get(DetailCreditModel::class.java)
        viewBinding.run {
            this.detailCreditModel = detailCreditModel
            this.lifecycleOwner = this@DetailCreditActivity
        }

        creditResponse = intent.getSerializableExtra(DETAIL_CREDIT) as CreditResponse
        detailCreditModel.creditResponse.observe(this, Observer {

        })

        setView()
    }

    fun setView() {
        val ivMenuLeft: ImageView = coustomActionBar.findViewById(R.id.iv_menu_left)
        val tvMenuName: TextView = coustomActionBar.findViewById(R.id.tv_menu_name)
        val ivMenuRight: ImageView = coustomActionBar.findViewById(R.id.iv_menu_right)
        ivMenuLeft.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.common_text_white))
        tvMenuName.setTextColor(ContextCompat.getColor(mContext, R.color.common_text_white))
        ivMenuRight.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.common_text_white))


        viewBinding.rgDetailCreditBtn.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_detail_cast -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fl_contents, MovieDetailCreditCastFragment.newInstance(creditResponse))
                        .commitAllowingStateLoss()
                }

                R.id.rb_detail_crew -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fl_contents, MovieDetailCreditCrewFragment.newInstance(creditResponse))
                        .commitAllowingStateLoss()
                }
            }
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_contents, MovieDetailCreditCastFragment.newInstance(creditResponse))
            .commitAllowingStateLoss()
    }

}