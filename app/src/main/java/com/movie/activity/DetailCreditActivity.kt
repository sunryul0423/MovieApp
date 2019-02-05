package com.movie.activity

import android.content.res.ColorStateList
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import com.movie.R
import com.movie.common.constants.MovieConstant
import com.movie.fragment.MovieDetailCreditCastFragment
import com.movie.fragment.MovieDetailCreditCrewFragment
import com.movie.model.data.CreditResponse

class DetailCreditActivity : BaseActivity() {

    private lateinit var creditResponse: CreditResponse

    private lateinit var rgDetailCreditBtn: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_credit)
        initActionBar(true, intent.getStringExtra(MovieConstant.MOVIE_NAME))
        creditResponse = intent.getSerializableExtra(MovieConstant.DETAIL_CREDIT) as CreditResponse
        setView()
    }

    fun setView() {
        val ivMenuLeft: ImageView = coustomActionBar.findViewById(R.id.iv_menu_left)
        val tvMenuName: TextView = coustomActionBar.findViewById(R.id.tv_menu_name)
        val ivMenuRight: ImageView = coustomActionBar.findViewById(R.id.iv_menu_right)
        ivMenuLeft.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.common_text_white))
        tvMenuName.setTextColor(ContextCompat.getColor(mContext, R.color.common_text_white))
        ivMenuRight.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.common_text_white))

        rgDetailCreditBtn = findViewById(R.id.rg_detail_credit_btn)
        rgDetailCreditBtn.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_detail_cast -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fl_contents, MovieDetailCreditCastFragment.newInstance(creditResponse)).commitAllowingStateLoss()
                }

                R.id.rb_detail_crew -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fl_contents, MovieDetailCreditCrewFragment.newInstance(creditResponse)).commitAllowingStateLoss()
                }
            }
        }
        supportFragmentManager.beginTransaction().replace(R.id.fl_contents, MovieDetailCreditCastFragment.newInstance(creditResponse)).commitAllowingStateLoss()
    }

}