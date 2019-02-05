package com.movie.customview.adapter

import android.content.Context
import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.movie.R
import com.movie.common.constants.MovieConstant
import com.movie.model.data.CreaditInfoModel
import jp.wasabeef.glide.transformations.CropCircleTransformation

class CreditListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    //layout
    private val llCreditListItem: LinearLayout = itemView.findViewById(R.id.ll_credit_list_item)
    private val ivCreditImg: ImageView = itemView.findViewById(R.id.iv_credit_img)
    private val tvCreditName: TextView = itemView.findViewById(R.id.tv_credit_name)
    private val tvCreditPart: TextView = itemView.findViewById(R.id.tv_credit_part)

    internal fun setData(mContext: Context, creaditInfoList: MutableList<CreaditInfoModel>, position: Int) {
        llCreditListItem.setOnClickListener {
            //            val intent = Intent(mContext, DetailMovieActivity::class.java)
//            intent.putExtra(MovieConstant.MOVIE_ID, movieList[position].id)
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

        if (creaditInfoList[position].url.isNullOrEmpty()) {
            Glide.with(mContext)
                    .load(R.drawable.profile)
                    .bitmapTransform(CropCircleTransformation(bitmapPool))
                    .placeholder(R.drawable.profile)
                    .into(ivCreditImg)
        } else {
            val url = MovieConstant.IMAGE_URL + creaditInfoList[position].url
            Glide.with(mContext)
                    .load(url)
                    .override(600, 600)
                    .centerCrop()
                    .bitmapTransform(CropCircleTransformation(bitmapPool))
                    .placeholder(R.drawable.profile)
                    .error(R.drawable.profile)
                    .into(ivCreditImg)
        }
        tvCreditName.text = creaditInfoList[position].name
        tvCreditPart.text = creaditInfoList[position].part
    }
}

class CreditListAdapter(private val mContext: Context, private var creaditInfoList: MutableList<CreaditInfoModel>) : RecyclerView.Adapter<CreditListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditListHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.view_credit_list_item, parent, false)
        return CreditListHolder(view)
    }

    override fun onBindViewHolder(holder: CreditListHolder, position: Int) {
        holder.setData(mContext, creaditInfoList, position)
    }

    override fun getItemCount(): Int {
        return creaditInfoList.size
    }

}

