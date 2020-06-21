import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.inhuis.R
import com.example.inhuis.database.Ingredient
import com.example.inhuis.database.amountTypes
import com.example.inhuis.ui.recipes.Recipe
import org.json.JSONException
import java.util.*

class ACIngredientsAdapter(
    context: Context,
    private val layoutResource: Int,
    private val ingredients: List<Ingredient>
) : ArrayAdapter<Ingredient>(context, 0, ingredients) {

    private var suggestions: List<Ingredient> = listOf()

    override fun getItem(position: Int): Ingredient? {
        return suggestions[position]
    }

    override fun getCount(): Int {
        return suggestions.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {

            override fun convertResultToString(resultValue: Any?): CharSequence {
                val value = resultValue as Ingredient
                return value.name
            }

            override fun publishResults(
                charSequence: CharSequence?,
                filterResults: Filter.FilterResults
            ) {
                if (filterResults.values !== null) {
                    suggestions = filterResults.values as List<Ingredient>
                    println(suggestions)
                    notifyDataSetChanged()
                }
            }

            override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults {
                suggestions = listOf()
                val queryString = charSequence?.toString()?.toLowerCase()

                val filterResults = FilterResults()
                val ingArray = arrayListOf<Ingredient>()
                val queue = Volley.newRequestQueue(context)
                val urlString =
                    "https://api.spoonacular.com/food/ingredients/autocomplete?query=$queryString&number=5&metaInformation=true&apiKey=37c8b0f2a77247fe8377c040537bc3ad"
                val jsonArrayRequest = JsonArrayRequest(
                    Request.Method.GET, urlString, null,
                    Response.Listener { response ->
                        for (i in 0 until response.length()) {
                            try {
                                var jsonObject = response.getJSONObject(i)
                                var id = jsonObject.getString("id").toInt()
                                var name = jsonObject.getString("name")
                                var imageURL =
                                    "https://spoonacular.com/cdn/ingredients_100x100/" + jsonObject.getString(
                                        "image"
                                    )
                                var possibleUnits = jsonObject.getJSONArray("possibleUnits")
                                Log.i("getData", name)
                                Log.i("getData", possibleUnits.toString())
                                var unit: amountTypes? = null

                                unit = if (possibleUnits.toString()
                                        .contains("quart") || possibleUnits.toString()
                                        .contains("fluid ounce")
                                ) {
                                    amountTypes.LITER
                                } else if (possibleUnits.toString().contains("piece")) {
                                    amountTypes.PCS
                                } else {
                                    amountTypes.GRAM
                                }

                                var ingredientObj = unit?.let { Ingredient(name, 0.0, imageURL, it) }
                                ingArray.add(ingredientObj)
                            } catch (e: JSONException) {

                            }
                        }
                        filterResults.values = ingArray
                        suggestions = ingArray
                        notifyDataSetChanged()
                    },
                    Response.ErrorListener { error ->
                        // TODO: Handle error
                        Log.e("getData", error.toString())
                    }
                )
                queue.add(jsonArrayRequest)

//                val filterResults = FilterResults()
//                filterResults.values = if (queryString == null || queryString.isEmpty())
//                    ingredients
//                else {
//                    ingredients.filter {
//                        it.name.toLowerCase().contains(queryString)
//                    }
//                }

                return filterResults
            }
        }
    }

    fun getAllItems(): List<Ingredient> {
        return suggestions
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row: View? = convertView;
        val inflater: LayoutInflater = LayoutInflater.from(context);

        if (row == null) row = inflater.inflate(R.layout.custom_autocomplete_layout, parent, false)

        val listEntry = suggestions.get(position);
        val searchItem: String = listEntry.name;

        val autoItem: TextView? = row?.findViewById(R.id.txt);
        autoItem?.setText(searchItem);

        // Get a reference to ImageView holder
        val icon: ImageView? = row!!.findViewById(R.id.flag);
        Glide.with(context).load(listEntry.image).into(icon!!)

        return row!!
    }


}