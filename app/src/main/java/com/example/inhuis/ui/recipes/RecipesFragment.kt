package com.example.inhuis.ui.recipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.inhuis.R
import com.example.inhuis.ui.ingredients.Ingredient
import org.json.JSONException


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
//        val textView: TextView = root.findViewById(R.id.text_recipes)
//        recipesViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        val recyclerViewRecipes: RecyclerView = root.findViewById(R.id.rvRecipes)
        recyclerViewRecipes.layoutManager = LinearLayoutManager(activity)

        recyclerViewRecipes.adapter = recipeAdapter
        // Populate the recipe list and notify the data set has changed.
        for (recipe in Recipe.RECIPES) {
            recipes.add(recipe)
        }
        recipeAdapter.notifyDataSetChanged()

        getData()

        return root
    }

    fun getData(){
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        val url = "https://api.spoonacular.com/recipes/findByIngredients?ingredients=spaghetti&number=3&apiKey=37c8b0f2a77247fe8377c040537bc3ad"

        var recipesArray: ArrayList<Recipe>? = null

        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                Log.i("getData", response.toString())
                Log.i("getData2", response.length().toString())
                for (i in 0 until response.length()) {
                    try {
                        var jsonObject = response.getJSONObject(i)
                        var id = jsonObject.getString("id").toInt()
                        var title = jsonObject.getString("title")
                        var imageURL = jsonObject.getString("image")
                        var missedIngredients = jsonObject.getJSONArray("missedIngredients")
                        var missedIngredientsArray: ArrayList<Ingredient>? = null
                        for(i in 0 until missedIngredients.length()){
                            try{
                                var missedIngredientObject = missedIngredients.getJSONObject(i)
                                var ingredientID = missedIngredientObject.getString("id").toInt()
                                var ingredientName = missedIngredientObject.getString("name")
                                var ingredientImageURL = missedIngredientObject.getString("image")
                                var ingredientAmount = missedIngredientObject.getString("amount").toInt()
                                missedIngredientsArray!!.add(Ingredient(ingredientID,ingredientName,ingredientImageURL,ingredientAmount))
                            } catch(e: JSONException) {

                            }
                        }
                        var usedIngredients = jsonObject.getJSONArray("usedIngredients")
                        var usedIngredientsArray: ArrayList<Ingredient>? = null
                        for(i in 0 until usedIngredients.length()){
                            try{
                                var usedIngredientObject = usedIngredients.getJSONObject(i)
                                var ingredientID = usedIngredientObject.getString("id").toInt()
                                var ingredientName = usedIngredientObject.getString("name")
                                var ingredientImageURL = usedIngredientObject.getString("image")
                                var ingredientAmount = usedIngredientObject.getString("amount").toInt()
                                usedIngredientsArray!!.add(Ingredient(ingredientID,ingredientName,ingredientImageURL,ingredientAmount))
                            } catch(e: JSONException) {

                            }
                        }
                        Log.i("getData3", jsonObject.toString())
                        Log.i("getData4", jsonObject.getString("title"))
                        recipesArray!!.add(Recipe(id,title,imageURL,usedIngredientsArray,missedIngredientsArray))
                        Log.i("getData4", recipesArray.toString())
                    } catch (e: JSONException) {

                    }
                }
            },
            Response.ErrorListener { error ->
                // TODO: Handle error
                Log.e("getData", error.toString())
            }
        )

        recipesArray = ArrayList()
        queue.add(jsonArrayRequest)

        //https://gist.github.com/cblunt/162beb7ecfafa1bd2ad9
    }
}