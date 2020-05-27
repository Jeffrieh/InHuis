package com.example.inhuis.database

import androidx.lifecycle.LiveData

class IngredientRepository(private val ingredientDao: IngredientDao) {

    val ingredients: LiveData<List<Ingredient>> = ingredientDao.getIngredients()

    suspend fun insert(ingredient: Ingredient) {
        ingredientDao.insert(ingredient)
    }
}