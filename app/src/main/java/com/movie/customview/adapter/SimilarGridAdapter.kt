package com.movie.customview.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.movie.R
import com.movie.activity.DetailMovieActivity
import com.movie.common.IMAGE_URL
import com.movie.common.MOVIE_ID
import com.movie.databinding.ViewSimilarListItemBinding
import com.movie.model.data.MovieMainResponse

class SimilarGridHolder(private val binding: ViewSimilarListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    private val context: Context = binding.root.context

    internal fun setData(similarList: List<MovieMainResponse.Movie>, position: Int) {
        binding.llSimilarListItem.setOnClickListener {
            val intent = Intent(context, DetailMovieActivity::class.java)
            intent.putExtra(MOVIE_ID, similarList[position].id)
            ActivityCompat.startActivity(context, intent, null)
        }
        val url = IMAGE_URL + similarList[position].posterPath
        Glide.with(context)
            .load(url)
            .override(context.resources.displayMetrics.widthPixels, context.resources.displayMetrics.widthPixels / 3)
            .centerCrop()
            .apply(RequestOptions.circleCropTransform())
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

    fun addList(addList: List<MovieMainResponse.Movie>, isAdd: Boolean) {
        if (!isAdd) {
            similarList.clear()
        }
        similarList.addAll(addList)
        notifyDataSetChanged()
    }

}

