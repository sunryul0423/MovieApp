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
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.movie.R
import com.movie.activity.DetailMovieActivity
import com.movie.common.constants.IMAGE_URL
import com.movie.common.constants.MOVIE_ID
import com.movie.databinding.ViewSimilarListItemBinding
import com.movie.model.data.MovieMainResponse
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class SimilarGridHolder(private val binding: ViewSimilarListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    private val context: Context = binding.root.context

    internal fun setData(similarList: List<MovieMainResponse.Movie>, position: Int) {
        binding.llSimilarListItem.setOnClickListener {
            val intent = Intent(context, DetailMovieActivity::class.java)
            intent.putExtra(MOVIE_ID, similarList[position].id)
            ActivityCompat.startActivity(context, intent, null)
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
        Glide.with(context)
            .load(url)
            .override(context.resources.displayMetrics.widthPixels, context.resources.displayMetrics.widthPixels / 3)
            .centerCrop()
            .bitmapTransform(
                RoundedCornersTransformation(
                    bitmapPool,
                    context.resources.getDimensionPixelSize(R.dimen.detail_rg_radius),
                    0
                )
            )
            .error(R.drawable.film_poster_placeholder)
            .placeholder(R.drawable.film_poster_placeholder)
            .into(binding.ivSimilarImg)

        binding.tvSimilarTitle.text = similarList[position].title
    }
}

class SimilarGridAdapter : RecyclerView.Adapter<SimilarGridHolder>() {

    private var similarList: MutableList<MovieMainResponse.Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarGridHolder {
        val binding: ViewSimilarListItemBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.view_similar_list_item, parent, false)
        return SimilarGridHolder(binding)
    }

    override fun onBindViewHolder(holder: SimilarGridHolder, position: Int) {
        holder.setData(similarList, position)
    }

    override fun getItemCount(): Int {
        return similarList.size
    }

    fun addList(addList: MutableList<MovieMainResponse.Movie>, isAdd: Boolean) {
        if (!isAdd) {
            similarList.clear()
        }
        similarList.addAll(addList)
        notifyDataSetChanged()
    }

}

