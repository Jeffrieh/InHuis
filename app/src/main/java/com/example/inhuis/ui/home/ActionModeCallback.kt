package com.example.inhuis.ui.home

import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.fragment.findNavController
import com.example.inhuis.R

class ActionModeCallback(actionMode : ActionMode) : ActionMode.Callback {
    var shouldResetRecyclerView = true
    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        when (item?.getItemId()) {
            R.id.action_get_recipes -> {
//                actionMode?.finish();
//                parentFragment?.findNavController()?.navigate(R.id.navigation_notifications)
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
//        actionMode = null
//            shouldResetRecyclerView = true
    }
}