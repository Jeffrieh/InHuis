package com.example.inhuis.ui.recipes

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.example.inhuis.R
import com.example.inhuis.helpers.FontManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.single_recipe.view.*


class RecipeAdapter(
    private val recipes: List<Recipe>,
    private val itemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(recipe: Recipe, clickListener: OnItemClickListener) {
            itemView.setOnClickListener {
                clickListener.onItemClicked(recipe)
            }
            itemView.singleRecipeTitle.text = recipe.title
            var numberOfUsedIngredients = recipe.ingredientsUsed.size
            var ingredientsUsedString = ""
            for (i in 0 until numberOfUsedIngredients) {
                ingredientsUsedString += recipe.ingredientsUsed[i].name
                ingredientsUsedString += " " + R.string.fa_check_solid
                ingredientsUsedString += " " + "\uf00c"
                ingredientsUsedString += "\n"
            }
            itemView.singleRecipeUsedIngredients.text = ingredientsUsedString
            var numberOfMissingIngredients = recipe.ingredientsMissing.size
            var ingredientsMissingString = ""
            for (i in 0 until numberOfMissingIngredients) {
                ingredientsMissingString += recipe.ingredientsMissing[i].name
                ingredientsMissingString += "\n"

            }
            itemView.singleRecipeMissingIngredients.text = ingredientsMissingString
            Picasso.get()
                .load(recipe.imageURL)
                .resize(500, 500)
                .into(itemView.singleRecipeImage)
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
        holder.bind(recipes[position], itemClickListener)
    }
}

interface OnItemClickListener{
    fun onItemClicked(recipe: Recipe)
}