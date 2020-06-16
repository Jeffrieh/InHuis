package com.example.inhuis.ui.recipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.inhuis.MainActivity
import com.example.inhuis.R
import com.example.inhuis.ui.ingredients.Ingredient
import com.example.inhuis.ui.ingredients.IngredientsViewModel
import org.json.JSONException


class RecipesFragment() : Fragment(), OnItemClickListener {

    private lateinit var recipesViewModel: RecipesViewModel
    private lateinit var ingredientsViewModel: IngredientsViewModel

    private val recipes = arrayListOf<Recipe>()
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        recipeAdapter = RecipeAdapter(recipes, this);

        val root = inflater.inflate(R.layout.fragment_recipes, container, false)

        ingredientsViewModel = ViewModelProvider({ requireActivity().viewModelStore }).get(IngredientsViewModel::class.java);
        recipesViewModel = ViewModelProvider({ requireActivity().viewModelStore }).get(RecipesViewModel::class.java);

        // returns a list of Ingredients()
        var listOfIngredients = ingredientsViewModel.ingredients.value
        Log.i("getData", listOfIngredients.toString())
        var ingredientsString = ""
        for (i in 0 until listOfIngredients!!.count()) {
            ingredientsString += if (i == 0) {
                listOfIngredients?.get(i)?.name
            } else {
                "," + listOfIngredients?.get(i)?.name
            }
        }
        ingredientsString = ingredientsString.toLowerCase()
        Log.i("getData", ingredientsString)

        val recyclerViewRecipes: RecyclerView = root.findViewById(R.id.rvRecipes)
        recyclerViewRecipes.layoutManager = LinearLayoutManager(activity)

        recyclerViewRecipes.adapter = recipeAdapter
        // Populate the recipe list and notify the data set has changed.
        // TURN THIS ON FOR MOCKUP DATA
//        for (recipe in Recipe.RECIPES) {
//            recipes.add(recipe)
//        }
        // TURN THIS ON FOR LIVE DATA FROM THE API: THIS COSTS API POINTS SO BE CAREFULL WITH IT (150 points per day are available and each request is 1 point + 0.1 point per item returned)
        getRecipes(ingredientsString)

        return root
    }

    private fun getRecipes(ingredientsString: String) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        // The URL to get the data
        // ingredients	string	apples,flour,sugar	A comma-separated list of ingredients that the recipes should contain.
        // number	number	10	The maximum number of recipes to return (between 1 and 100). Defaults to 10.
        // limitLicense	boolean	true	Whether the recipes should have an open license that allows display with proper attribution.
        // ranking	number	1	Whether to maximize used ingredients (1) or minimize missing ingredients (2) first.
        // ignorePantry	boolean	true	Whether to ignore typical pantry items, such as water, salt, flour, etc.
        val url =
            "https://api.spoonacular.com/recipes/findByIngredients?ingredients=$ingredientsString&number=3&ranking=1&apiKey=37c8b0f2a77247fe8377c040537bc3ad"

        Log.i("getData", url)

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

    override fun onItemClicked(recipe: Recipe) {
        this.recipesViewModel.selectedRecipeId = recipe.id;
        System.out.println(this.recipesViewModel.selectedRecipeId);
        parentFragment?.findNavController()?.navigate(R.id.navigation_itemDetail)
    }

}