package com.example.inhuis.ui.recipes

data class Recipe(
    var id: Int,
    var title: String,
    var imageURL: String,
    var ingredients: String
) {
    companion object {
        val RECIPES = arrayOf<Recipe>(
            Recipe(1, "Test Title 1", "test-url-1", "Test Ingrediënts 1"),
            Recipe(2, "Test Title 2", "test-url-2", "Test Ingrediënts 2"),
            Recipe(3, "Test Title 3", "test-url-3", "Test Ingrediënts 3"),
            Recipe(4, "Test Title 4", "test-url-4", "Test Ingrediënts 4"),
            Recipe(5, "Test Title 5", "test-url-5", "Test Ingrediënts 5")
        )
    }
}