package com.example.inhuis.database

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class IngredientRepository(private val ingredientDao: IngredientDao) {

    val ingredients: LiveData<List<Ingredient>> = ingredientDao.getIngredients()

    suspend fun insert(ingredient: Ingredient) {
        ingredientDao.insert(ingredient)
    }

    suspend fun delete(ingredient: Ingredient) {
        ingredientDao.delete(ingredient)
    }
}