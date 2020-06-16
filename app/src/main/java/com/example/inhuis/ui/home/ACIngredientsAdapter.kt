import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.inhuis.R
import com.example.inhuis.database.Ingredient

class ACIngredientsAdapter(
    context: Context,
    private val layoutResource: Int,
    private val ingredients: List<Ingredient>
) : ArrayAdapter<Ingredient>(context, 0, ingredients) {

    private var suggestions: List<Ingredient> = listOf()

    override fun getItem(position: Int): Ingredient? {
        return super.getItem(position);
    }


    override fun getCount(): Int {
        return suggestions.size
    }

    fun getListOfItems(): List<Ingredient> {
        return ingredients;
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
                suggestions = filterResults.values as List<Ingredient>
                println(suggestions)
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults {
                val queryString = charSequence?.toString()?.toLowerCase()

                val filterResults = FilterResults()
                filterResults.values = if (queryString == null || queryString.isEmpty())
                    ingredients
                else {
                    ingredients.filter {
                        it.name.toLowerCase().contains(queryString)

                    }
                }


                return filterResults
            }
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        println(position)
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

        return row!!;


    }

}