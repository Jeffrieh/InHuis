package com.example.inhuis.ui.recipesdetail

import com.example.inhuis.database.Ingredient
import com.example.inhuis.database.amountTypes

data class RecipeDetail(
    var id: Int,
    var title: String,
    var imageURL: String,
    var ingredients: ArrayList<Ingredient>,
    var timeToMake: String,
    var numberOfServings: Int,
    var summary: String,
    var instructions: String
) {

    companion object {
        val RECIPE = RecipeDetail(1, "Test Title 1", "https://spoonacular.com/recipeImages/532783-312x231.jpg", arrayListOf<Ingredient>(Ingredient("Test Ingredient 1", 40,"https://spoonacular.com/recipeImages/532783-312x231.jpg", amountTypes.GRAM), Ingredient("Test Ingredient 2", 66,"https://spoonacular.com/recipeImages/532783-312x231.jpg", amountTypes.GRAM)),"12minutes",4,"Test summary", "Test instructions")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RecipeDetail

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}