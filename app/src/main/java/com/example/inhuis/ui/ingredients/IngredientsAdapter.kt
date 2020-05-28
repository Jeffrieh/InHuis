import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.inhuis.R
import com.example.inhuis.database.Ingredient
import kotlinx.android.synthetic.main.ingredient_item.view.*

class IngredientsAdapter(private var myDataset: List<Ingredient>) :
    RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(myDataset[position])
    }


    override fun getItemCount() = myDataset.size

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(ingredient: Ingredient) {
            itemView.name.text = ingredient.name
            itemView.amount.text = "-" + ingredient.amount.toString() + "g"
        }

    }


}