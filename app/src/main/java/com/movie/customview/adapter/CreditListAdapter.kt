package com.movie.customview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.movie.R
import com.movie.databinding.ViewCreditListItemBinding
import com.movie.model.view.CreaditInfoViewModel

class CreditListHolder(private val binding: ViewCreditListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private val context: Context = binding.root.context

    internal fun setData(creaditInfoList: List<CreaditInfoViewModel>, position: Int) {
        binding.llCreditListItem.setOnClickListener {
            //            val intent = Intent(context, DetailMovieActivity::class.java)
//            intent.putExtra(MOVIE_ID, movieList[position].id)
//            ActivityCompat.startActivity(context, intent, null)
        }
    }
}

class CreditListAdapter : RecyclerView.Adapter<CreditListHolder>() {

    private lateinit var binding: ViewCreditListItemBinding

    private var creaditInfoList: List<CreaditInfoViewModel> = mutableListOf()

    fun setItem(_creaditInfoList: List<CreaditInfoViewModel>?) {
        creaditInfoList = if (_creaditInfoList.isNullOrEmpty()) {
            mutableListOf()
        } else {
            _creaditInfoList
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditListHolder {
        binding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.view_credit_list_item, parent, false)
        return CreditListHolder(binding)
    }

    override fun getItemCount(): Int {
        return creaditInfoList.size
    }

    override fun onBindViewHolder(holder: CreditListHolder, position: Int) {
        binding.creaditInfoViewModel = creaditInfoList[position]
        holder.setData(creaditInfoList, position)
    }

}

