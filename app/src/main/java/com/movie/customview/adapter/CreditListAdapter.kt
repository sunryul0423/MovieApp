package com.movie.customview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.movie.R
import com.movie.databinding.ViewCreditListItemBinding
import com.movie.model.view.CreditInfoViewModel

class CreditListHolder(binding: ViewCreditListItemBinding) : RecyclerView.ViewHolder(binding.root)

class CreditListAdapter : RecyclerView.Adapter<CreditListHolder>() {

    private lateinit var binding: ViewCreditListItemBinding

    private var creditInfoList: List<CreditInfoViewModel> = mutableListOf()

    fun setItem(_creditInfoList: List<CreditInfoViewModel>?) {
        creditInfoList = if (_creditInfoList.isNullOrEmpty()) {
            mutableListOf()
        } else {
            _creditInfoList
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditListHolder {
        binding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.view_credit_list_item, parent, false)
        return CreditListHolder(binding)
    }

    override fun getItemCount(): Int {
        return creditInfoList.size
    }

    override fun onBindViewHolder(holder: CreditListHolder, position: Int) {
        binding.creditInfoViewModel = creditInfoList[position]
    }

}

