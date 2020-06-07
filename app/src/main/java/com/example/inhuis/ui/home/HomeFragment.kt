package com.example.inhuis.ui.home

import HomeAdapter
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inhuis.R
import com.example.inhuis.database.Ingredient
import com.example.inhuis.ui.ingredients.IngredientsViewModel
import kotlinx.android.synthetic.main.dialog_add_ingredient.view.*
import kotlinx.android.synthetic.main.dialog_add_ingredient.view.etAmount
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var ingredientsViewModel: IngredientsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        ingredientsViewModel = ViewModelProviders.of(this).get(IngredientsViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerView: RecyclerView = root.findViewById(R.id.rvIngredients)
        recyclerView.layoutManager = LinearLayoutManager(root.context)
        recyclerView.setHasFixedSize(true)

//        val ingredients = listOf<Ingredient>(Ingredient("kip", 5));

//        recyclerView.adapter = HomeAdapter(ingredients, root.context);

        ingredientsViewModel.ingredients.observe(viewLifecycleOwner, Observer { ingredients ->
            val adapter = HomeAdapter(ingredients, root.context);
            recyclerView.adapter = adapter;
        })

        //Floating action button on the home screen.
        val fab: View = root.findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            val alertDialog: AlertDialog? = activity?.let {
                val builder = AlertDialog.Builder(it)
                val inflater = requireActivity().layoutInflater;
                val layout = inflater.inflate(R.layout.dialog_add_ingredient, null)
                builder.setView(layout)

                val spinner: Spinner = layout.findViewById(R.id.ingredients)
                ArrayAdapter.createFromResource(
                    layout.context,
                    R.array.ingredients_array,
                    android.R.layout.simple_spinner_item
                ).also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = adapter
                }

                layout.btnAdd.setOnClickListener { view ->
                    val t = Ingredient(spinner.selectedItem.toString(), Integer.parseInt(layout.etAmount.text.toString()))
                    ingredientsViewModel.insert(t)
                }

                builder.create()
            }

            alertDialog?.show()

        }




        return root
    }


}