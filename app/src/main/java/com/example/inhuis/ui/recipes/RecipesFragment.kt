package com.example.inhuis.ui.recipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.inhuis.MainActivity
import com.example.inhuis.R
import com.example.inhuis.ui.ingredients.Ingredient
import com.example.inhuis.ui.ingredients.IngredientsFragment
import com.example.inhuis.ui.ingredients.IngredientsViewModel
import org.json.JSONException


class RecipesFragment() : Fragment() {

    private lateinit var recipesViewModel: RecipesViewModel
    private lateinit var ingredientsViewModel: IngredientsViewModel

    private val recipes = arrayListOf<Recipe>()
    private val recipeAdapter = RecipeAdapter(recipes)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        recipesViewModel =
            ViewModelProviders.of(this).get(RecipesViewModel::class.java)

//        ingredientsViewModel =
//            ViewModelProviders.of(this).get(IngredientsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_recipes, container, false)
//        val textView: TextView = root.findViewById(R.id.text_recipes)
//        recipesViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        val viewModel = activity?.run {
            ViewModelProviders.of(this).get(IngredientsViewModel::class.java)
        } ?: throw Exception("Invalid Activity")


//        val model = root?.let { ViewModelProviders.of(this).get(IngredientsViewModel::class.java) }
        val model =
            ViewModelProvider({ requireActivity().viewModelStore }).get(IngredientsViewModel::class.java);

        println("observing");
        model?.selectedIngredients.observe(viewLifecycleOwner, Observer { list -> println("test" + list) });

        val recyclerViewRecipes: RecyclerView = root.findViewById(R.id.rvRecipes)
        recyclerViewRecipes.layoutManager = LinearLayoutManager(activity)

        recyclerViewRecipes.adapter = recipeAdapter
        // Populate the recipe list and notify the data set has changed.
        // TURN THIS ON FOR MOCKUP DATA
        for (recipe in Recipe.RECIPES) {
            recipes.add(recipe)
        }
        // TURN THIS ON FOR LIVE DATA FROM THE API: THIS COSTS API POINTS SO BE CAREFULL WITH IT (150 points per day are available and each request is 1 point + 0.1 point per item returned)
        //getRecipes()

        return root
    }

    private fun getRecipes() {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        // The URL to get the data
        // TODO: make the url variables dynamic from step before this
        val url =
            "https://api.spoonacular.com/recipes/findByIngredients?ingredients=spaghetti&number=3&apiKey=37c8b0f2a77247fe8377c040537bc3ad"

        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                for (i in 0 until response.length()) {
                    try {
                        var jsonObject = response.getJSONObject(i)
                        var id = jsonObject.getString("id").toInt()
                        var title = jsonObject.getString("title")
                        var imageURL = jsonObject.getString("image")
                        var missedIngredients = jsonObject.getJSONArray("missedIngredients")
                        var missedIngredientsArray = arrayListOf<Ingredient>()
                        for (i in 0 until missedIngredients.length()) {
                            try {
                                var missedIngredientObject = missedIngredients.getJSONObject(i)
                                var ingredientID = missedIngredientObject.getString("id").toInt()
                                var ingredientName = missedIngredientObject.getString("name")
                                var ingredientImageURL = missedIngredientObject.getString("image")
                                var ingredientAmount =
                                    missedIngredientObject.getString("amount").toDouble()
                                missedIngredientsArray.add(
                                    Ingredient(
                                        ingredientID,
                                        ingredientName,
                                        ingredientImageURL,
                                        ingredientAmount
                                    )
                                )
                            } catch (e: JSONException) {

                            }
                        }
                        var usedIngredients = jsonObject.getJSONArray("usedIngredients")
                        var usedIngredientsArray = arrayListOf<Ingredient>()
                        for (i in 0 until usedIngredients.length()) {
                            try {
                                var usedIngredientObject = usedIngredients.getJSONObject(i)
                                var ingredientID = usedIngredientObject.getString("id").toInt()
                                var ingredientName = usedIngredientObject.getString("name")
                                var ingredientImageURL = usedIngredientObject.getString("image")
                                var ingredientAmount =
                                    usedIngredientObject.getString("amount").toDouble()
                                usedIngredientsArray.add(
                                    Ingredient(
                                        ingredientID,
                                        ingredientName,
                                        ingredientImageURL,
                                        ingredientAmount
                                    )
                                )
                            } catch (e: JSONException) {

                            }
                        }
                        recipes.add(
                            Recipe(
                                id,
                                title,
                                imageURL,
                                usedIngredientsArray,
                                missedIngredientsArray
                            )
                        )
                    } catch (e: JSONException) {

                    }
                }
                recipeAdapter.notifyDataSetChanged()
                Log.i("getData", recipes.toString())

            },
            Response.ErrorListener { error ->
                // TODO: Handle error
                Log.e("getData", error.toString())
            }
        )

        queue.add(jsonArrayRequest)
    }
}