package com.example.inhuis.ui.home

import android.app.AlertDialog
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
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
import com.example.inhuis.R
import com.example.inhuis.database.Ingredient
import com.example.inhuis.database.IngredientRepository
import com.example.inhuis.ui.ingredients.IngredientsViewModel
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.dialog_add_ingredient.*
import kotlinx.android.synthetic.main.dialog_add_ingredient.view.*
import kotlinx.android.synthetic.main.dialog_add_ingredient.view.etAmount
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil.setContentView
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment(){

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var ingredientsViewModel: IngredientsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        ingredientsViewModel = ViewModelProviders.of(this).get(IngredientsViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)

        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
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