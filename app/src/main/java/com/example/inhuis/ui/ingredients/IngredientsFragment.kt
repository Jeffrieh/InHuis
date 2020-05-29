package com.example.inhuis.ui.ingredients

import IngredientsAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inhuis.R
import com.example.inhuis.database.Ingredient


class IngredientsFragment : Fragment() {

//    @BindingAdapter(value = ["setAdapter"])
//    fun RecyclerView.bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<*>) {
//        this.run {
//            this.setHasFixedSize(true)
//            this.adapter = adapter
//        }
//    }

    private var tracker: SelectionTracker<Long>? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val ingredientsViewModel by viewModels<IngredientsViewModel>()
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
//        val textView: TextView = root.findViewById(R.id.text_dashboard)

        val recyclerView: RecyclerView = root.findViewById(R.id.rvIngredients)
        recyclerView.layoutManager = LinearLayoutManager(root.context)
        recyclerView.setHasFixedSize(true)

        ingredientsViewModel.ingredients.observe(viewLifecycleOwner, Observer { ingredients ->
            val adapter = IngredientsAdapter(ingredients, root.context)
            recyclerView.adapter = adapter

            tracker = SelectionTracker.Builder(
                "selection-1",
                recyclerView,
                StableIdKeyProvider(recyclerView),
                IngredientsLookup(recyclerView),
                StorageStrategy.createLongStorage()
            ).withSelectionPredicate(
                SelectionPredicates.createSelectAnything()
            ).build()

            adapter.setTracker(tracker)

        })


        return root

    }
}