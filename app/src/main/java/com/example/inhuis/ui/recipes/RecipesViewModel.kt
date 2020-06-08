package com.example.inhuis.ui.recipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecipesViewModel : ViewModel() {
    var selectedRecipeId : Int = 0;
    fun select(recipeId: Int) {
        selectedRecipeId = recipeId
    }
}