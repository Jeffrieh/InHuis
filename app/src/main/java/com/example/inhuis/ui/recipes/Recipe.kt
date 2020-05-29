package com.example.inhuis.ui.recipes

import com.example.inhuis.ui.ingredients.Ingredient

data class Recipe(
    var id: Int,
    var title: String,
    var imageURL: String,
    var ingredientsUsed: ArrayList<Ingredient>,
    var ingredientsMissing: ArrayList<Ingredient>
) {

    companion object {
        val RECIPES = arrayOf<Recipe>(
            Recipe(1, "Test Title 1", "https://spoonacular.com/recipeImages/532783-312x231.jpg", arrayListOf<Ingredient>(Ingredient(1, "Test Ingredient 1", "https://spoonacular.com/recipeImages/532783-312x231.jpg", 2.0), Ingredient(2, "Test Ingredient 2", "https://spoonacular.com/recipeImages/532783-312x231.jpg", 4.0)), arrayListOf<Ingredient>(Ingredient(3, "Test Ingredient 3", "https://spoonacular.com/recipeImages/532783-312x231.jpg", 3.0), Ingredient(4, "Test Ingredient 4", "https://spoonacular.com/recipeImages/532783-312x231.jpg", 1.0))),
            Recipe(1, "Test Title 1", "https://spoonacular.com/recipeImages/532783-312x231.jpg", arrayListOf<Ingredient>(Ingredient(1, "Test Ingredient 1", "https://spoonacular.com/recipeImages/532783-312x231.jpg", 2.0), Ingredient(2, "Test Ingredient 2", "https://spoonacular.com/recipeImages/532783-312x231.jpg", 4.0)), arrayListOf<Ingredient>(Ingredient(3, "Test Ingredient 3", "https://spoonacular.com/recipeImages/532783-312x231.jpg", 3.0), Ingredient(4, "Test Ingredient 4", "https://spoonacular.com/recipeImages/532783-312x231.jpg", 1.0))),
            Recipe(1, "Test Title 1", "https://spoonacular.com/recipeImages/532783-312x231.jpg", arrayListOf<Ingredient>(Ingredient(1, "Test Ingredient 1", "https://spoonacular.com/recipeImages/532783-312x231.jpg", 2.0), Ingredient(2, "Test Ingredient 2", "https://spoonacular.com/recipeImages/532783-312x231.jpg", 4.0)), arrayListOf<Ingredient>(Ingredient(3, "Test Ingredient 3", "https://spoonacular.com/recipeImages/532783-312x231.jpg", 3.0), Ingredient(4, "Test Ingredient 4", "https://spoonacular.com/recipeImages/532783-312x231.jpg", 1.0))),
            Recipe(1, "Test Title 1", "https://spoonacular.com/recipeImages/532783-312x231.jpg", arrayListOf<Ingredient>(Ingredient(1, "Test Ingredient 1", "https://spoonacular.com/recipeImages/532783-312x231.jpg", 2.0), Ingredient(2, "Test Ingredient 2", "https://spoonacular.com/recipeImages/532783-312x231.jpg", 4.0)), arrayListOf<Ingredient>(Ingredient(3, "Test Ingredient 3", "https://spoonacular.com/recipeImages/532783-312x231.jpg", 3.0), Ingredient(4, "Test Ingredient 4", "https://spoonacular.com/recipeImages/532783-312x231.jpg", 1.0))),
            Recipe(1, "Test Title 1", "https://spoonacular.com/recipeImages/532783-312x231.jpg", arrayListOf<Ingredient>(Ingredient(1, "Test Ingredient 1", "https://spoonacular.com/recipeImages/532783-312x231.jpg", 2.0), Ingredient(2, "Test Ingredient 2", "https://spoonacular.com/recipeImages/532783-312x231.jpg", 4.0)), arrayListOf<Ingredient>(Ingredient(3, "Test Ingredient 3", "https://spoonacular.com/recipeImages/532783-312x231.jpg", 3.0), Ingredient(4, "Test Ingredient 4", "https://spoonacular.com/recipeImages/532783-312x231.jpg", 1.0)))
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Recipe

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}