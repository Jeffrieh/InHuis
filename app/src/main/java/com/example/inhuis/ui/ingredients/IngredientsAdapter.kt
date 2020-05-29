import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.example.inhuis.R
import com.example.inhuis.database.Ingredient
import com.example.inhuis.ui.ingredients.IngredientsViewHolder
import kotlinx.android.synthetic.main.ingredient_item.view.*

class IngredientsAdapter(private var myDataset: List<Ingredient>, private val context: Context) :
    RecyclerView.Adapter<IngredientsViewHolder>() {

    private var tracker: SelectionTracker<Long>? = null

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder =
        IngredientsViewHolder(
            LayoutInflater.from(context).inflate(R.layout.ingredient_item, parent, false)
        )

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount() = myDataset.size

    fun setTracker(tracker: SelectionTracker<Long>?) {
        this.tracker = tracker
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        holder.name.text = myDataset[position].name
        holder.amount.text = myDataset[position].amount.toString()
        val parent = holder.name.parent as LinearLayout

        if (tracker!!.isSelected(position.toLong())) {
            parent.background = ColorDrawable(
                Color.parseColor("#80deea")
            )
        } else {
            // Reset color to black if not selected
            parent.background = ColorDrawable(Color.BLACK)
        }
    }

}