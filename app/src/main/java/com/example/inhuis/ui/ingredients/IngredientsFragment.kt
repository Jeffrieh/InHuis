package com.example.inhuis.ui.ingredients

import IngredientsAdapter
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inhuis.MainActivity
import com.example.inhuis.R
import com.example.inhuis.database.Ingredient
import com.example.inhuis.ui.recipes.RecipesFragment
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import java.util.*
import java.util.Arrays.stream


class IngredientsFragment : Fragment() {

    var actionMode: ActionMode? = null

    inner class ActionModeCallback : ActionMode.Callback {
        var shouldResetRecyclerView = true
        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            when (item?.getItemId()) {
                R.id.action_get_recipes -> {
                    actionMode?.finish();
                    //TODO : redirect to other fragment and pass ingredients data

//                    var bundle = Bundle()
//                    bundle.putParcelableArrayList("ingredients", ArrayList(selectedIngredients))

                    parentFragment?.findNavController()?.navigate(R.id.navigation_notifications)
                    return true
                }
            }
            return false
        }

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            val inflater = mode?.getMenuInflater()
            inflater?.inflate(R.menu.action_mode_menu, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            menu?.findItem(R.id.action_get_recipes)?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            return true
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
//            if (shouldResetRecyclerView) {
//                myAdapter?.selectedIds?.clear()
//                myAdapter?.notifyDataSetChanged()
//            }
//            isMultiSelectOn = false
            println("destroying")
            actionMode = null
//            shouldResetRecyclerView = true
        }
    }

    private var tracker: SelectionTracker<Long>? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val ingredientsViewModel by requireActivity().viewModels<IngredientsViewModel>()
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
//        val textView: TextView = root.findViewById(R.id.text_dashboard)

        val recyclerView: RecyclerView = root.findViewById(R.id.rvIngredients)
        recyclerView.layoutManager = LinearLayoutManager(root.context)
        recyclerView.setHasFixedSize(true)

        val ingredients = arrayListOf<Ingredient>();

        val adapter = IngredientsAdapter(ingredients, root.context)

        adapter.onItemClick = {
            ingredientsViewModel.updateIngredient(it as List<Ingredient>)
        }

        recyclerView.adapter = adapter

        ingredientsViewModel.ingredients.observe(viewLifecycleOwner, Observer { ingredients ->
            adapter.updateItems(ingredients)
            recyclerView.adapter = adapter

            println("actionmode is ${actionMode}")

            val showAction = ingredients.map { t -> t.checked }.contains(true)
            if (showAction) {
                actionMode = view?.startActionMode(ActionModeCallback())
            } else {
                println("were stopping")
                println(actionMode)
                actionMode?.finish()
            }
        })

        return root

    }
}

