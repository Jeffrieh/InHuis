import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.inhuis.R
import com.example.inhuis.database.Ingredient
import android.widget.ArrayAdapter as ArrayAdapter

class ACIngredientsAdapter(
    context: Context,
    private val layoutResource: Int,
    private val ingredients: List<Ingredient>
) : ArrayAdapter<Ingredient>(context, layoutResource, ingredients) {

    override fun getItem(position: Int): Ingredient? {
        return super.getItem(position);
    }

    fun getListOfItems() : List<Ingredient>{
        return ingredients;
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row: View? = convertView;
        val inflater: LayoutInflater = LayoutInflater.from(context);

        if (row == null) row = inflater.inflate(R.layout.custom_autocomplete_layout, parent, false)

        val listEntry = ingredients.get(position);
        val searchItem : String = listEntry.name;

        val autoItem : TextView? = row?.findViewById (R.id.txt);
        autoItem?.setText(searchItem);

        // Get a reference to ImageView holder
        val icon : ImageView? = row?.findViewById (R.id.flag);
        icon?.setImageDrawable(context.getDrawable(listEntry.image));

        return row!!;

    }

}