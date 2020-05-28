package com.example.inhuis.ui.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inhuis.R
import kotlinx.android.synthetic.main.fragment_recipes.*

class RecipesFragment : Fragment() {

    private lateinit var recipesViewModel: RecipesViewModel
    private val recipes = arrayListOf<Recipe>()
    private val recipeAdapter = RecipeAdapter(recipes)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        recipesViewModel =
            ViewModelProviders.of(this).get(RecipesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_recipes, container, false)
        val textView: TextView = root.findViewById(R.id.text_recipes)
        recipesViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        initViews()
        return root
    }

    private fun initViews() {
//        rvRecipes.layoutManager = LinearLayoutManager(this@, RecyclerView.VERTICAL, false)
        rvRecipes.adapter = recipeAdapter
        // Populate the recipe list and notify the data set has changed.
        for (recipe in Recipe.RECIPES) {
            recipes.add(recipe)
        }
        recipeAdapter.notifyDataSetChanged()
    }
}