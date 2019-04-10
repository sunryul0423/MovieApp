package com.movie.customview.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.movie.R
import com.movie.activity.DetailMovieActivity
import com.movie.common.constants.IMAGE_URL
import com.movie.common.constants.MOVIE_ID
import com.movie.model.data.MovieMainResponse
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class SimilarGridHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    //layout
    private val llSimilarListItem: LinearLayout = itemView.findViewById(R.id.ll_similar_list_item)
    private val ivSimilarImg: ImageView = itemView.findViewById(R.id.iv_similar_img)
    private val tvSimilarTitle: TextView = itemView.findViewById(R.id.tv_similar_title)

    internal fun setData(mContext: Context, similarList: List<MovieMainResponse.Movie>, position: Int) {
        llSimilarListItem.setOnClickListener {
            val intent = Intent(mContext, DetailMovieActivity::class.java)
            intent.putExtra(MOVIE_ID, similarList[position].id)
            ActivityCompat.startActivity(mContext, intent, null)
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
        val url = IMAGE_URL + similarList[position].posterPath
        Glide.with(mContext)
            .load(url)
            .override(mContext.resources.displayMetrics.widthPixels, mContext.resources.displayMetrics.widthPixels / 3)
            .centerCrop()
            .bitmapTransform(
                RoundedCornersTransformation(
                    bitmapPool,
                    mContext.resources.getDimensionPixelSize(R.dimen.detail_rg_radius),
                    0
                )
            )
            .error(R.drawable.film_poster_placeholder)
            .placeholder(R.drawable.film_poster_placeholder)
            .into(ivSimilarImg)

        tvSimilarTitle.text = similarList[position].title
    }
}

class SimilarGridAdapter(private val mContext: Context, private var similarList: MutableList<MovieMainResponse.Movie>) :
    RecyclerView.Adapter<SimilarGridHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarGridHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.view_similar_list_item, parent, false)
        return SimilarGridHolder(view)
    }

    override fun onBindViewHolder(holder: SimilarGridHolder, position: Int) {
        holder.setData(mContext, similarList, position)
    }

    override fun getItemCount(): Int {
        return similarList.size
    }

    fun addList(addList: MutableList<MovieMainResponse.Movie>) {
        similarList.addAll(addList)
        notifyDataSetChanged()
    }

}

