package com.example.inhuis.ui.ingredients

import android.view.View
import android.widget.TextView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.ingredient_item.view.*

class IngredientsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.name
    val amount: TextView = view.amount

    fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
        object : ItemDetailsLookup.ItemDetails<Long>() {
            override fun getPosition(): Int = adapterPosition
            override fun getSelectionKey(): Long? = itemId
        }

}