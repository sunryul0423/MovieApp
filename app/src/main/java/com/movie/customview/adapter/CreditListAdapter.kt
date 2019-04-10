package com.movie.customview.adapter

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.movie.R
import com.movie.common.constants.IMAGE_URL
import com.movie.databinding.ViewCreditListItemBinding
import com.movie.model.data.CreaditInfoModel
import jp.wasabeef.glide.transformations.CropCircleTransformation

class CreditListHolder(private val binding: ViewCreditListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    internal fun setData(mContext: Context, creaditInfoList: MutableList<CreaditInfoModel>, position: Int) {
        binding.llCreditListItem.setOnClickListener {
            //            val intent = Intent(mContext, DetailMovieActivity::class.java)
//            intent.putExtra(MOVIE_ID, movieList[position].id)
//            ActivityCompat.startActivity(mContext, intent, null)
        }
        val bitmapPool = object : BitmapPool {
            override fun setSizeMultiplier(p0: Float) {
            }

            override fun trimMemory(p0: Int) {
            }

            override fun get(p0: Int, p1: Int, p2: Bitmap.Config?): Bitmap? {
                return null
            }

            override fun clearMemory() {
            }

            override fun getDirty(p0: Int, p1: Int, p2: Bitmap.Config?): Bitmap? {
                return null
            }

            override fun getMaxSize(): Int {
                return 0
            }

            override fun put(p0: Bitmap?): Boolean {
                return false
            }
        }

        if (creaditInfoList[position].url.isEmpty()) {
            Glide.with(mContext)
                .load(R.drawable.profile)
                .bitmapTransform(CropCircleTransformation(bitmapPool))
                .placeholder(R.drawable.profile)
                .into(binding.ivCreditImg)
        } else {
            val url = IMAGE_URL + creaditInfoList[position].url
            Glide.with(mContext)
                .load(url)
                .override(600, 600)
                .centerCrop()
                .bitmapTransform(CropCircleTransformation(bitmapPool))
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(binding.ivCreditImg)
        }
        binding.tvCreditName.text = creaditInfoList[position].name
        binding.tvCreditPart.text = creaditInfoList[position].part
    }
}

class CreditListAdapter(private val mContext: Context, private var creaditInfoList: MutableList<CreaditInfoModel>) :
    RecyclerView.Adapter<CreditListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditListHolder {
        val binding: ViewCreditListItemBinding =
            DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.view_credit_list_item, parent, false)
        return CreditListHolder(binding)
    }

    override fun onBindViewHolder(holder: CreditListHolder, position: Int) {
        holder.setData(mContext, creaditInfoList, position)
    }

    override fun getItemCount(): Int {
        return creaditInfoList.size
    }

}

