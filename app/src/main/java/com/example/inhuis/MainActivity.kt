package com.example.inhuis

import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.inhuis.ui.ingredients.IngredientsViewModel
import com.example.inhuis.ui.recipes.RecipesViewModel

class MainActivity : AppCompatActivity() {

    lateinit var toolbar_title: TextView
    lateinit var toolbar: Toolbar
    var actionMode: ActionMode? = null
    lateinit var ingredientsViewModel: IngredientsViewModel
    lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        this.toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar_title = findViewById(R.id.toolbar_title)

        supportActionBar?.apply {
            toolbar_title.text = "Ingredients"
            // Display the app icon in action bar/toolbar
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(true)
            setDisplayUseLogoEnabled(true)
            setDisplayHomeAsUpEnabled(true);
        }

        val iv: IngredientsViewModel by viewModels();
        ingredientsViewModel = iv;

        val t: RecipesViewModel by viewModels();

        navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)

    }

    fun startActionMode() {
        actionMode = startActionMode(ActionModeCallback())
    }

    fun stopActionMode() {
        actionMode?.finish()
        actionMode = null
    }

    inner class ActionModeCallback : ActionMode.Callback {
        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            when (item?.getItemId()) {
                R.id.action_get_recipes -> {
                    actionMode?.finish();
                    navController.navigate(R.id.navigation_notifications)
                    return true
                }
            }
            return false
        }

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            val inflater = mode?.menuInflater
            inflater?.inflate(R.menu.action_mode_menu, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            menu?.findItem(R.id.action_get_recipes)?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            return true
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            actionMode = null
        }
    }

    fun changeTitle(title: String) {
        supportActionBar.apply {
            toolbar_title.text = title
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}



