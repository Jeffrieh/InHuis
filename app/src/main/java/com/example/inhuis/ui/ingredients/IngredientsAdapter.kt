import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.inhuis.R
import com.example.inhuis.database.Ingredient
import kotlinx.android.synthetic.main.ingredient_item.view.*

class IngredientsAdapter(private var ingredients: List<Ingredient>, private val context: Context) :
    RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {

    private var tracker: SelectionTracker<Long>? = null
    var onItemClick: ((Any) -> Unit)? = null

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

    fun updateItems(ingredients: List<Ingredient>) {
        this.ingredients = ingredients
    }

    fun getItemAt(position: Int): Ingredient {
        return ingredients[position];
    }

    override fun getItemCount() = ingredients.size

    fun setTracker(tracker: SelectionTracker<Long>?) {
        this.tracker = tracker
    }

    inner class IngredientsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Ingredient, selected: Boolean?) = with(itemView) {
            try {
                itemView.setOnClickListener {
                    item.seChecked(!item.checked)
                    onItemClick?.invoke(ingredients)
                }

                itemView.ivCheck.visibility = if (item.checked) View.VISIBLE else View.GONE
                Glide.with(context).load(item.image).into(itemView.imageView)
                itemView.tvName.text = item.name;
                itemView.tvAmount.text = " - " + item.amount.toString() + item.amountType.value
            } catch (e: Exception) {
                Log.e("error", e.toString())
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
        holder.bind(ingredients[position], tracker?.isSelected(position.toLong()));
    }

}
