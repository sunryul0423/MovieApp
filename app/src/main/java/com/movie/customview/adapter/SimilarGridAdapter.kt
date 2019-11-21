package com.movie.customview.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movie.R
import com.movie.activity.DetailMovieActivity
import com.movie.databinding.ViewSimilarListItemBinding
import com.movie.model.data.MovieMainResponse
import com.movie.util.IMAGE_URL
import com.movie.util.MOVIE_ID

class SimilarGridHolder(private val binding: ViewSimilarListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    private val context: Context = binding.root.context

    internal fun setData(similarData: MovieMainResponse.Movie?) {
        similarData?.let {
            binding.llSimilarListItem.setOnClickListener {
                val intent = Intent(context, DetailMovieActivity::class.java)
                intent.putExtra(MOVIE_ID, similarData.id)
                ActivityCompat.startActivity(context, intent, null)
            }

            val url = IMAGE_URL + similarData.posterPath
            Glide.with(context)
                .load(url)
                .override(
                    context.resources.displayMetrics.widthPixels,
                    context.resources.displayMetrics.widthPixels / 3
                )
                .fitCenter()
//                .apply(
//                    RequestOptions.bitmapTransform(
//                        RoundedCornersTransformation(
//                            null,
//                            context.resources.getDimensionPixelSize(R.dimen.detail_rg_radius),
//                            0
//                        )
//                    )
//                )
                .error(R.drawable.film_poster_placeholder)
                .placeholder(R.drawable.film_poster_placeholder)
                .into(binding.ivSimilarImg)

            binding.tvSimilarTitle.text = similarData.title
        }

    }
}

class SimilarGridAdapter : PagedListAdapter<MovieMainResponse.Movie, SimilarGridHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<MovieMainResponse.Movie>() {
            override fun areItemsTheSame(oldItem: MovieMainResponse.Movie, newItem: MovieMainResponse.Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieMainResponse.Movie, newItem: MovieMainResponse.Movie)
                    : Boolean {
                return oldItem.id == newItem.id && oldItem.title == newItem.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarGridHolder {
        val binding = ViewSimilarListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SimilarGridHolder(binding)
    }

    override fun onBindViewHolder(holder: SimilarGridHolder, position: Int) {
        holder.setData(getItem(position))
    }
}

