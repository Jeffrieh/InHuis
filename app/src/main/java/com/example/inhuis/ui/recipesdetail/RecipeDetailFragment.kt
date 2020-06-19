package com.example.inhuis.ui.recipesdetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.inhuis.R
import com.example.inhuis.database.Ingredient
import com.example.inhuis.database.amountTypes
import com.example.inhuis.ui.ingredients.IngredientsViewModel
import com.example.inhuis.ui.recipes.RecipesViewModel
import com.squareup.picasso.Picasso
import org.json.JSONException
import java.util.zip.Inflater

class RecipeDetailFragment() : Fragment() {

    private lateinit var recipesViewModel: RecipesViewModel

    private lateinit var recipe: RecipeDetail

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        recipesViewModel = ViewModelProvider({ requireActivity().viewModelStore }).get(
            RecipesViewModel::class.java);

        //get the selected recipe id via :
        var recipeID = recipesViewModel.selectedRecipeId
        Log.i("getData", recipesViewModel.selectedRecipeId.toString())

        val root = inflater.inflate(R.layout.fragment_recipedetail, container, false)

        // TURN THIS OFF FOR LIVE DATA AND ON FOR MOCKUP DATA
        val titleText: TextView = root.findViewById(R.id.RecipeDetailTitle)
        val ingredientsTitleText: TextView = root.findViewById(R.id.recipeDetailIngredientsTitle)
        val ingredientsText: TextView = root.findViewById(R.id.RecipeDetailIngredients)
        val summaryText: TextView = root.findViewById(R.id.recipeDetailSummary)
        val instructionsText: TextView = root.findViewById(R.id.recipeDetailInstructions)
        val timeText: TextView = root.findViewById(R.id.recipeDetailTime)
        val image: ImageView = root.findViewById(R.id.RecipeDetailImage)

        // TURN THIS OFF FOR LIVE DATA AND ON FOR MOCKUP DATA
         recipe = RecipeDetail.RECIPE

        // TURN THIS OFF FOR LIVE DATA AND ON FOR MOCKUP DATA
//        titleText.text = recipe.title
//        ingredientsTitleText.text = "Ingredients ( $recipe.numberOfServings servings )"
//        var numberOfIngredients = recipe.ingredients.size
//        var ingredientsString = ""
//        for (i in 0 until numberOfIngredients){
//            ingredientsString += recipe.ingredients[i].name
//            ingredientsString += "\n"
//        }
//        ingredientsText.text = ingredientsString
//        ingredientsText.text = ingredientsUsedString + "\n" + ingredientsMissingString
//        summaryText.text = recipe.summary
//        instructionsText.text = recipe.instructions
//        timeText.text = recipe.timeToMake
//        Picasso.get()
//            .load(recipe.imageURL)
//            .resize(500, 500)
//            .into(image)

        // TURN THIS OFF FOR MOCKUP DATA AND TURN THIS ON FOR LIVE DATA FROM THE API: THIS COSTS API POINTS SO BE CAREFULL WITH IT (150 points per day are available and each request is 1 point + 0.1 point per item returned)
        getRecipe(recipeID, root)

        return root
    }

    private fun getRecipe(recipeID: Int, root: View) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        // The URL to get the data
        // TODO: error checking in the JSON, because id was null for instance
        val url =
            "https://api.spoonacular.com/recipes/$recipeID/information?includeNutrition=false&apiKey=37c8b0f2a77247fe8377c040537bc3ad"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                    try {
                        var jsonObject = response
                        Log.i("getData", jsonObject.toString())
                        var id = jsonObject.getString("id").toInt()
                        var title = jsonObject.getString("title")
                        var imageURL = jsonObject.getString("image")
                        var ingredients = jsonObject.getJSONArray("extendedIngredients")
                        var ingredientsArray = arrayListOf<Ingredient>()
                        for (i in 0 until ingredients.length()) {
                            try {
                                var ingredientObject = ingredients.getJSONObject(i)
                                var ingredientID = ingredientObject.getString("id").toInt()
                                var ingredientName = ingredientObject.getString("name")
                                var ingredientImageURL = ingredientObject.getString("image")
                                var ingredientAmount =
                                    ingredientObject.getString("amount").toDouble()
                                    ingredientsArray.add(
                                        Ingredient(
                                            ingredientName,
                                            ingredientAmount.toInt(),
                                            ingredientImageURL,
                                            amountTypes.GRAM
                                        )
                                    )
                            } catch (e: JSONException) {

                            }
                        }

                        var timeToMake = jsonObject.getString("readyInMinutes")
                        var numberOfServings = jsonObject.getString("servings").toInt()
                        var summary = jsonObject.getString("summary")
                        var instructions = jsonObject.getString("instructions")
                        recipe = RecipeDetail(
                                id,
                                title,
                                imageURL,
                                ingredientsArray,
                                timeToMake,
                                numberOfServings,
                                summary,
                                instructions
                            )

                        Log.i("getDataDetail", recipe.toString())

                        val titleText: TextView = root.findViewById(R.id.RecipeDetailTitle)
                        val ingredientsTitleText: TextView = root.findViewById(R.id.recipeDetailIngredientsTitle)
                        val ingredientsText: TextView = root.findViewById(R.id.RecipeDetailIngredients)
                        val summaryText: TextView = root.findViewById(R.id.recipeDetailSummary)
                        val instructionsText: TextView = root.findViewById(R.id.recipeDetailInstructions)
                        val timeText: TextView = root.findViewById(R.id.recipeDetailTime)
                        val image: ImageView = root.findViewById(R.id.RecipeDetailImage)

                        titleText.text = title
                        ingredientsTitleText.text = "Ingredients ( ${recipe.numberOfServings} servings )"

                        var numberOfIngredients = recipe.ingredients.size
                        var ingredientsString = ""
                        for (i in 0 until numberOfIngredients){
                            ingredientsString += recipe.ingredients[i].name
                            ingredientsString += "\n"
                        }
                        ingredientsText.text = ingredientsString
                        summaryText.text = recipe.summary
                        instructionsText.text = recipe.instructions
                        timeText.text = recipe.timeToMake + " minutes"
                        Picasso.get()
                            .load(recipe.imageURL)
                            .resize(500, 500)
                            .into(image)

                    } catch (e: JSONException) {
                        Log.e("getData2", e.toString())
                    }

            },
            Response.ErrorListener { error ->
                // TODO: Handle error
                Log.e("getData", error.toString())
            }
        )

        queue.add(jsonObjectRequest)
    }
}