package com.example.inhuis.ui.recipes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.inhuis.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.single_recipe.view.*


class RecipeAdapter (private val recipes: List<Recipe>) :
    RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(recipe: Recipe) {
            itemView.singleRecipeTitle.text = recipe.title
            var numberOfUsedIngredients = recipe.ingredientsUsed.size
            var ingredientsUsedString = ""
            for (i in 0 until numberOfUsedIngredients){
                ingredientsUsedString = ingredientsUsedString + ", " + recipe.ingredientsUsed[i].name
            }
            itemView.singleRecipeUsedIngredients.text = ingredientsUsedString
            var numberOfMissingIngredients = recipe.ingredientsMissing.size
            var ingredientsMissingString = ""
            for (i in 0 until numberOfMissingIngredients){
                ingredientsMissingString = ingredientsMissingString + ", " + recipe.ingredientsMissing[i].name
            }
            itemView.singleRecipeMissingIngredients.text = ingredientsMissingString
            Picasso.get().load(recipe.imageURL).into(itemView.singleRecipeImage)
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

