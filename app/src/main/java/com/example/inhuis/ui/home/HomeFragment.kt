package com.example.inhuis.ui.home

import ACIngredientsAdapter
import HomeAdapter
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.ActionMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inhuis.MainActivity
import com.example.inhuis.R
import com.example.inhuis.database.Ingredient
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.dialog_add_ingredient.view.*


class HomeFragment : Fragment() {

    private lateinit var ingredientsViewModel: IngredientsViewModel
    private lateinit var textView: AutoCompleteTextView;
    private lateinit var measurementText: TextView;
    private lateinit var amountText: TextView;
    private lateinit var allowedIngredients: List<Ingredient>;
    private var isErrorMessage: Boolean = false;
    private lateinit var mainActivity: MainActivity

    var actionMode: ActionMode? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = requireActivity() as MainActivity

        val ingredientsViewModel by mainActivity.viewModels<IngredientsViewModel>()
        this.ingredientsViewModel = ingredientsViewModel
        mainActivity.toolbar_title.text = "My Ingredients"

        // Create a list for autocomplete
        allowedIngredients = listOf<Ingredient>();

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
                    Snackbar
                        .make(recyclerView, "Ingredient Removed", Snackbar.LENGTH_LONG)
                        .setAction("Undo") {
                            ingredientsViewModel.insert(ing)
                        }
                        .show()
                }

            }


        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        val adapter = HomeAdapter(listOf(), root.context);
        recyclerView.adapter = adapter;

        adapter.onItemClick = {
            ingredientsViewModel.updateIngredient(it as List<Ingredient>)
        }

        ingredientsViewModel.ingredients.observe(viewLifecycleOwner, Observer { ingredients ->
            adapter.updateItems(ingredients)
            itemTouchHelper.attachToRecyclerView(recyclerView);

            val showAction = ingredients.map { t -> t.checked }.contains(true)
            if (showAction) {
                if (mainActivity?.actionMode == null) mainActivity.startActionMode()
            } else {
                mainActivity.stopActionMode()
            }

            var selected = ingredients.filter { e -> e.checked }
            mainActivity.actionMode?.title = if (selected.count() < 3) selected.map { e -> e.name }
                .joinToString() else "${selected.count()} Items Selected"

        })


        //Floating action button on the home screen.
        val fab: View = root.findViewById(R.id.fab)
        fab.setOnClickListener { view ->

            val arrayAdapter = ACIngredientsAdapter(
                requireActivity(), R.layout.custom_autocomplete_layout,
                allowedIngredients
            )

            val alertDialog: AlertDialog? = activity?.let {
                val builder = AlertDialog.Builder(it)
                val inflater = requireActivity().layoutInflater;
                val layout = inflater.inflate(R.layout.dialog_add_ingredient, null)

                builder.setView(layout)

                measurementText = layout.findViewById(R.id.measurement) as TextView
                amountText = layout.findViewById(R.id.etAmount) as TextView

                textView = layout.findViewById(R.id.tvingredient) as AutoCompleteTextView
                textView.setAdapter(arrayAdapter)
                allowedIngredients = arrayAdapter.getAllItems()
                textView.setOnClickListener {
                    textView.showDropDown()
                    Log.i("Logging", allowedIngredients.toString())
                }
                builder.setPositiveButton("OK") { dialog, button ->
                    try {
                        var selectedIngredient = allowedIngredients.find { ingredient ->
                            textView.text.toString().equals(ingredient.name)
                        }

                        println(selectedIngredient!!.amountType)
                        val t = Ingredient(
                            textView.text.toString(),
                            layout.etAmount.text.toString().toDouble(),
                            selectedIngredient!!.image,
                            selectedIngredient!!.amountType
                        )
                        Log.i("Logging", t.toString())
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
                    allowedIngredients = arrayAdapter.getAllItems()
                    val names = allowedIngredients.map { e -> e.name }
                    valid = names.contains(s.toString());
                    alertDialog?.getButton(AlertDialog.BUTTON_POSITIVE)?.isEnabled = valid;
                    if (valid) {
                        var ing = allowedIngredients.find { e -> e.name == s.toString() }
                        measurementText.text = ing?.amountType?.name
                        amountText.text = ing?.amount.toString()
                    }
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