package com.example.inhuis.ui.ingredients

import IngredientsAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.inhuis.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class IngredientsFragment : Fragment() {

    private lateinit var ingredientsViewModel: IngredientsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ingredientsViewModel = ViewModelProviders.of(this).get(IngredientsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
//        val textView: TextView = root.findViewById(R.id.text_dashboard)


        val recyclerView : RecyclerView = root.findViewById(R.id.rvIngredients)

        ingredientsViewModel.ingredients.observe(viewLifecycleOwner, Observer { ingredients ->
            recyclerView.also {
                it.layoutManager = LinearLayoutManager(requireContext())
                it.setHasFixedSize(true)
                it.adapter = IngredientsAdapter(ingredients)
            }
        })
        return root

    }
}