package com.example.inhuis.ui.ingredients

import android.app.Application
import androidx.lifecycle.*
import androidx.recyclerview.selection.Selection
import com.example.inhuis.database.Ingredient
import com.example.inhuis.database.IngredientDatabase
import com.example.inhuis.database.IngredientRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IngredientsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: IngredientRepository

    val ingredients: MediatorLiveData<List<Ingredient>> = MediatorLiveData()

    init {
        val ingredientDao = IngredientDatabase.getDatabase(application).ingredientDao()
        repository = IngredientRepository(ingredientDao)
        this.ingredients.addSource(repository.ingredients) { result: List<Ingredient>? ->
            result?.let { ingredients.value = result }
        }
    }

    fun updateIngredient(ingredients: List<Ingredient>) {
        this.ingredients.value = ingredients
    }

    fun insert(ingredient: Ingredient) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(ingredient)
    }

    fun delete(ingredient: Ingredient) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(ingredient)
    }


}
