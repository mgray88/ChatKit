package com.stfalcon.chatkit.messages

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class DefaultItemDecoration(private val whiteSpace: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.getChildAdapterPosition(view) != 0){
            outRect.bottom = whiteSpace
        }

    }
}