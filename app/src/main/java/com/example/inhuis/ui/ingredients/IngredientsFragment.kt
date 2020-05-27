package com.example.inhuis.ui.ingredients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.inhuis.R

class IngredientsFragment : Fragment() {

    private lateinit var ingredientsViewModel: IngredientsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ingredientsViewModel =
            ViewModelProviders.of(this).get(IngredientsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        ingredientsViewModel.text.observe(this, Observer {
            textView.text = it
        })

        return root
    }
}