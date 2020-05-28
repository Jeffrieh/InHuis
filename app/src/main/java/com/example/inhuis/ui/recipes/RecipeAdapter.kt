package com.example.inhuis.ui.recipes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.inhuis.R
import kotlinx.android.synthetic.main.single_recipe.view.*


class RecipeAdapter (private val recipes: List<Recipe>) :
    RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    lateinit var context: Context

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(recipe: Recipe) {
            itemView.singleRecipeTitle.text = recipe.title
            itemView.singleRecipeText.text = recipe.ingredients
            itemView.singleRecipeImage.setImageResource(R.drawable.round_add_black_24dp)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.single_recipe, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(recipes[position])
    }
}

