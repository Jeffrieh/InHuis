package com.example.inhuis.ui.ingredients

import android.app.Application
import androidx.lifecycle.*
import com.example.inhuis.database.Ingredient
import com.example.inhuis.database.IngredientDatabase
import com.example.inhuis.database.IngredientRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IngredientsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: IngredientRepository
    val ingredients: LiveData<List<Ingredient>>

    init {
        val ingredientDao = IngredientDatabase.getDatabase(application).ingredientDao()
        repository = IngredientRepository(ingredientDao)
        ingredients = repository.ingredients
    }

    fun insert(ingredient: Ingredient) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(ingredient)
    }

}