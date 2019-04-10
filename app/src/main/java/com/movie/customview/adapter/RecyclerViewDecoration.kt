package com.movie.customview.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.movie.model.data.RecyclerViewSpacing

class RecyclerViewDecoration(private val isGrid: Boolean, var spanCount: Int, var spacing: RecyclerViewSpacing) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount
        outRect.left = column * spacing.left / spanCount
        outRect.right = spacing.right - (column + 1) * spacing.right / spanCount
        if (isGrid && position >= spanCount) {
            outRect.top = spacing.top // item top
        }
    }

}