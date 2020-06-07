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
import com.example.inhuis.R
import com.example.inhuis.database.Ingredient
import kotlinx.android.synthetic.main.ingredient_item.view.*

class IngredientsAdapter(private var myDataset: List<Ingredient>, private val context: Context) :
    RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {

    private var tracker: SelectionTracker<Long>? = null

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder =
        IngredientsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item, parent, false)
        )

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount() = myDataset.size

    fun setTracker(tracker: SelectionTracker<Long>?) {
        this.tracker = tracker
    }

    inner class IngredientsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Ingredient, selected: Boolean) = with(itemView) {
            itemView.imageView.setImageDrawable(context.getDrawable(item.image));
            itemView.tvName.text = item.name;
            itemView.tvAmount.text = item.amount.toString();

            if (selected) {
                itemView.background = ColorDrawable(
                    Color.parseColor("#80deea")
                )
            } else {
                itemView.background = ColorDrawable(Color.BLACK)
            }
            //setOnClickListener { listener(item) }
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Long? = itemId
            }

    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        holder.bind(myDataset[position], tracker!!.isSelected(position.toLong()));
    }

}