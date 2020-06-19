package com.example.inhuis.ui.recipes

import com.example.inhuis.database.Ingredient
import com.example.inhuis.database.amountTypes


data class Recipe(
    var id: Int,
    var title: String,
    var imageURL: String,
    var ingredientsUsed: ArrayList<Ingredient>,
    var ingredientsMissing: ArrayList<Ingredient>
) {

    companion object {
        val RECIPES = arrayOf<Recipe>(
            Recipe(
                1,
                "Test Title 1",
                "https://spoonacular.com/recipeImages/532783-312x231.jpg",
                arrayListOf<Ingredient>(
                    Ingredient(
                        "Test Ingredient 1",
                        12,
                        "https://spoonacular.com/recipeImages/532783-312x231.jpg",
                        amountTypes.GRAM
                    ),
                    Ingredient(
                        "Apple",
                        500,
                        "https://spoonacular.com/recipeImages/532783-312x231.jpg",
                        amountTypes.GRAM
                    )
                ),
                arrayListOf<Ingredient>(
                    Ingredient(
                        "Test ingredient 2",
                        300,
                        "https://spoonacular.com/recipeImages/532783-312x231.jpg",
                        amountTypes.GRAM
                    ),
                    Ingredient(
                        "Test Ingredient 4",
                        500,
                        "https://spoonacular.com/recipeImages/532783-312x231.jpg",
                        amountTypes.GRAM
                    )
                )
            ),
            Recipe(
                1,
                "Test Title 1",
                "https://spoonacular.com/recipeImages/532783-312x231.jpg",
                arrayListOf<Ingredient>(
                    Ingredient(
                        "Test Ingredient 1",
                        12,
                        "https://spoonacular.com/recipeImages/532783-312x231.jpg",
                        amountTypes.GRAM
                    ),
                    Ingredient(
                        "Apple",
                        500,
                        "https://spoonacular.com/recipeImages/532783-312x231.jpg",
                        amountTypes.GRAM
                    )
                ),
                arrayListOf<Ingredient>(
                    Ingredient(
                        "Test ingredient 2",
                        300,
                        "https://spoonacular.com/recipeImages/532783-312x231.jpg",
                        amountTypes.GRAM
                    ),
                    Ingredient(
                        "Test Ingredient 4",
                        500,
                        "https://spoonacular.com/recipeImages/532783-312x231.jpg",
                        amountTypes.GRAM
                    )
                )
            ),
            Recipe(
                1,
                "Test Title 1",
                "https://spoonacular.com/recipeImages/532783-312x231.jpg",
                arrayListOf<Ingredient>(
                    Ingredient(
                        "Test Ingredient 1",
                        12,
                        "https://spoonacular.com/recipeImages/532783-312x231.jpg",
                        amountTypes.GRAM
                    ),
                    Ingredient(
                        "Apple",
                        500,
                        "https://spoonacular.com/recipeImages/532783-312x231.jpg",
                        amountTypes.GRAM
                    )
                ),
                arrayListOf<Ingredient>(
                    Ingredient(
                        "Test ingredient 2",
                        300,
                        "https://spoonacular.com/recipeImages/532783-312x231.jpg",
                        amountTypes.GRAM
                    ),
                    Ingredient(
                        "Test Ingredient 4",
                        500,
                        "https://spoonacular.com/recipeImages/532783-312x231.jpg",
                        amountTypes.GRAM
                    )
                )
            ),
            Recipe(
                1,
                "Test Title 1",
                "https://spoonacular.com/recipeImages/532783-312x231.jpg",
                arrayListOf<Ingredient>(
                    Ingredient(
                        "Test Ingredient 1",
                        12,
                        "https://spoonacular.com/recipeImages/532783-312x231.jpg",
                        amountTypes.GRAM
                    ),
                    Ingredient(
                        "Apple",
                        500,
                        "https://spoonacular.com/recipeImages/532783-312x231.jpg",
                        amountTypes.GRAM
                    )
                ),
                arrayListOf<Ingredient>(
                    Ingredient(
                        "Test ingredient 2",
                        300,
                        "https://spoonacular.com/recipeImages/532783-312x231.jpg",
                        amountTypes.GRAM
                    ),
                    Ingredient(
                        "Test Ingredient 4",
                        500,
                        "https://spoonacular.com/recipeImages/532783-312x231.jpg",
                        amountTypes.GRAM
                    )
                )
            )
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