package com.example.inhuis.ui.recipesdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecipeDetailViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is recipe detail fragment"
    }

    val text: LiveData<String> = _text
}