package com.example.inhuis.ui.home

import ACIngredientsAdapter
import HomeAdapter
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inhuis.R
import com.example.inhuis.database.Ingredient
import com.example.inhuis.ui.ingredients.IngredientsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.dialog_add_ingredient.view.*
import kotlinx.android.synthetic.main.ingredient_item.view.*


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var ingredientsViewModel: IngredientsViewModel
    private lateinit var textView: AutoCompleteTextView;
    private lateinit var allowedIngredients: List<Ingredient>;
    private var drwbl: Int = 0;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        ingredientsViewModel = ViewModelProviders.of(this).get(IngredientsViewModel::class.java)

        allowedIngredients = listOf<Ingredient>(
            Ingredient("Apple", 0, "https://www.foodandfriends.nl/upload/artikel/jm/appel-artikel.jpg"),
            Ingredient("Banana", 0, "https://d2z5yqacp5qgwg.cloudfront.net/app/uploads/2020/01/Be-bananen.jpg"),
            Ingredient("Garlic", 0, "https://lh3.googleusercontent.com/proxy/RdDvmxe29AT7VgaJueIAuD3eSdNBWIO_u4iVN6fzm5gu0vKdaDhQyBGFolofazAnKjX5QHgvA4OIO3MStODR-tIqWRTBK_5aBk2GX--dKXcgpJcBi42ACmVjPzPScomrdS6v7wZwwI8"),
            Ingredient("Chicken", 0, "https://w7.pngwing.com/pngs/336/200/png-transparent-chicken-meat-buffalo-wing-raw-foodism-chicken.png")
        );


        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerView: RecyclerView = root.findViewById(R.id.rvIngredients)
        recyclerView.layoutManager = LinearLayoutManager(root.context)
        recyclerView.setHasFixedSize(true)

        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {

                    return false
                }

                override fun isItemViewSwipeEnabled(): Boolean {
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val ing =
                        (viewHolder as HomeAdapter.HomeAdapterViewHolder).getIngredientAt(viewHolder.adapterPosition);
                    ingredientsViewModel.delete(ing);
                }

            }


        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)

        ingredientsViewModel.ingredients.observe(viewLifecycleOwner, Observer { ingredients ->
            val adapter = HomeAdapter(ingredients, root.context);
            recyclerView.adapter = adapter;
            itemTouchHelper.attachToRecyclerView(recyclerView);
        })

        //Floating action button on the home screen.
        val fab: View = root.findViewById(R.id.fab)
        fab.setOnClickListener { view ->

            val alertDialog: AlertDialog? = activity?.let {
                val builder = AlertDialog.Builder(it)
                val inflater = requireActivity().layoutInflater;
                val layout = inflater.inflate(R.layout.dialog_add_ingredient, null)

                builder.setView(layout)

                val arrayAdapter = ACIngredientsAdapter(
                    requireActivity(), R.layout.custom_autocomplete_layout,
                    allowedIngredients
                )

                Log.i("checkArray", arrayAdapter.toString())
                Log.i("checkArray", "TEST bericht")

                textView = layout.findViewById(R.id.tvingredient) as AutoCompleteTextView
                textView.setAdapter(arrayAdapter)
                textView.setOnClickListener { textView.showDropDown() }

                textView.setOnItemClickListener { parent, view, position, id ->
                    textView.setText(arrayAdapter.getItem(position)?.name)
//                    drwbl = arrayAdapter.getItem(position)?.image!!
                }

                var text: String

                builder.setPositiveButton("OK") { dialog, button ->
                    try {
                        val t = Ingredient(
                            textView.text.toString(),
                            Integer.parseInt(layout.etAmount.text.toString()),
                            allowedIngredients.find { ingredient -> textView.text.toString().equals(ingredient.name) }!!.image
                        )

                        ingredientsViewModel.insert(t)

                    } catch (e: Exception) {

                    } finally {
                        val snackbar = Snackbar.make(
                            root, "Added Ingredient",
                            Snackbar.LENGTH_SHORT
                        ).setAction("Action", null)
                        snackbar.setActionTextColor(Color.BLUE)
                        val snackbarView = snackbar.view
                        val textView =
                            snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
                        textView.setTextColor(Color.BLUE)
                        textView.textSize = 28f
                        snackbar.show()
                        dialog.dismiss();
                    }

                }

                builder.create()
            }

            var valid: Boolean;

            textView.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val names = allowedIngredients.map { e -> e.name }
                    valid = names.contains(s.toString());
                    alertDialog?.getButton(AlertDialog.BUTTON_POSITIVE)?.isEnabled = valid;
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {

                }
            });

            alertDialog?.show()
            alertDialog?.getButton(AlertDialog.BUTTON_POSITIVE)?.isEnabled = false;

        }


        return root
    }


}