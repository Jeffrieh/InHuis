package com.example.inhuis

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.inhuis.ui.ingredients.IngredientsViewModel
import com.example.inhuis.ui.recipes.RecipesViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            // Set toolbar title/app title
            title = "Toolbar Title"

            // Set action bar/toolbar sub title
            subtitle = "Toolbar sub title"


            toolbar_title.text = "testje"
            // Display the app icon in action bar/toolbar
            setDisplayShowHomeEnabled(true)
            setDisplayUseLogoEnabled(true)
        }

        val model: IngredientsViewModel by viewModels();
        val t: RecipesViewModel by viewModels();

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        
    }
}
