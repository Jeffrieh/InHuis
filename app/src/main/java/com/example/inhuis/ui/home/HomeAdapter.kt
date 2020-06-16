import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.inhuis.R
import com.example.inhuis.database.Ingredient
import kotlinx.android.synthetic.main.ingredient_item.view.*

class HomeAdapter(private var myDataset: List<Ingredient>, private val context: Context) :
    RecyclerView.Adapter<HomeAdapter.HomeAdapterViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapterViewHolder =
        HomeAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item, parent, false)
        )

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount() = myDataset.size

    inner class HomeAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Ingredient) = with(itemView) {
            try{
                itemView.tvName.text = item.name;
                Glide.with(context).load(item.image).into(itemView.imageView)
//                itemView.imageView.setImageDrawable(context.getDrawable(item.image))
                itemView.tvAmount.text = " - " + item.amount.toString() + "g";
            }catch(e : Exception){
                println(e.message);
            }
        }

        public fun getIngredientAt(position: Int): Ingredient {
            return myDataset[position];
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Long? = itemId
            }

    }

    override fun onBindViewHolder(holder: HomeAdapterViewHolder, position: Int) {
        holder.bind(myDataset[position]);
//        val parent = holder.name.parent as LinearLayout
//
//        if (tracker!!.isSelected(position.toLong())) {
//            parent.background = ColorDrawable(
//                Color.parseColor("#80deea")
//            )
//        } else {
//            // Reset color to black if not selected
//            parent.background = ColorDrawable(Color.BLACK)
//        }
    }

}