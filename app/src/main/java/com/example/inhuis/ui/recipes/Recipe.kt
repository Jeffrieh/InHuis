package com.example.inhuis.ui.recipes

import com.example.inhuis.ui.ingredients.Ingredient

data class Recipe(
    var id: Int,
    var title: String,
    var imageURL: String,
    var ingredientsUsed: ArrayList<Ingredient>?,
    var ingredientsMissing: ArrayList<Ingredient>?
) {

    companion object {
        val RECIPES = arrayOf<Recipe>(
            Recipe(1, "Test Title 1", "test-url-1", arrayListOf<Ingredient>(Ingredient(1, "Test Ingredient 1", "img-url-1", 2), Ingredient(2, "Test Ingredient 2", "img-url-2", 4)), arrayListOf<Ingredient>(Ingredient(3, "Test Ingredient 3", "img-url-3", 3), Ingredient(4, "Test Ingredient 4", "img-url-4", 1))),
            Recipe(1, "Test Title 1", "test-url-1", arrayListOf<Ingredient>(Ingredient(1, "Test Ingredient 1", "img-url-1", 2), Ingredient(2, "Test Ingredient 2", "img-url-2", 4)), arrayListOf<Ingredient>(Ingredient(3, "Test Ingredient 3", "img-url-3", 3), Ingredient(4, "Test Ingredient 4", "img-url-4", 1))),
            Recipe(1, "Test Title 1", "test-url-1", arrayListOf<Ingredient>(Ingredient(1, "Test Ingredient 1", "img-url-1", 2), Ingredient(2, "Test Ingredient 2", "img-url-2", 4)), arrayListOf<Ingredient>(Ingredient(3, "Test Ingredient 3", "img-url-3", 3), Ingredient(4, "Test Ingredient 4", "img-url-4", 1))),
            Recipe(1, "Test Title 1", "test-url-1", arrayListOf<Ingredient>(Ingredient(1, "Test Ingredient 1", "img-url-1", 2), Ingredient(2, "Test Ingredient 2", "img-url-2", 4)), arrayListOf<Ingredient>(Ingredient(3, "Test Ingredient 3", "img-url-3", 3), Ingredient(4, "Test Ingredient 4", "img-url-4", 1))),
            Recipe(1, "Test Title 1", "test-url-1", arrayListOf<Ingredient>(Ingredient(1, "Test Ingredient 1", "img-url-1", 2), Ingredient(2, "Test Ingredient 2", "img-url-2", 4)), arrayListOf<Ingredient>(Ingredient(3, "Test Ingredient 3", "img-url-3", 3), Ingredient(4, "Test Ingredient 4", "img-url-4", 1)))
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