package com.example.inhuis.ui.home

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.inhuis.R
import com.example.inhuis.database.Ingredient
import com.example.inhuis.database.IngredientRepository
import com.example.inhuis.ui.ingredients.IngredientsViewModel
import kotlinx.android.synthetic.main.dialog_add_ingredient.view.*


class HomeFragment : Fragment() {

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

        homeViewModel.text.observe(this, Observer {
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

                //we give suggestions of what the user can add.
                val suggestions = arrayOf("test", "hallo", "doei")
                val adapter = ArrayAdapter(layout.context, android.R.layout.simple_list_item_1, suggestions)

                layout.product_name.setAdapter(adapter)
                layout.btnAdd.setOnClickListener { view ->
                    val t = Ingredient("kip", 500)
                    ingredientsViewModel.insert(t)
                }

                builder.create()
            }

            alertDialog?.show()

        }




        return root
    }


}