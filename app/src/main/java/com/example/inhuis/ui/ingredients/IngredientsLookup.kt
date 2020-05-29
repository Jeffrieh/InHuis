package com.example.inhuis.ui.ingredients

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView

//changed string to long ?
class IngredientsLookup(private val rv: RecyclerView) : ItemDetailsLookup<Long>() {
    override fun getItemDetails(event: MotionEvent)
            : ItemDetails<Long>? {
        val view = rv.findChildViewUnder(event.x, event.y)
        if (view != null) {
            return (rv.getChildViewHolder(view) as IngredientsViewHolder).getItemDetails()
        }
        return null

    }
}