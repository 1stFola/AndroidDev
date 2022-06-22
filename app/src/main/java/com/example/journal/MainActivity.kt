package com.example.journal

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.ui.*
import com.example.journal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.nav_host_fragment_content_main)

        // retrieve our bottom nav bar from our binding
        val bottomNavBar = binding.bottomNavBar
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.home,
                R.id.search,
                R.id.recentlyDeleted,
                R.id.profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavBar.setupWithNavController(navController)

        navController.addOnDestinationChangedListener {_, destination, _ ->
            if (destination.id == R.id.login || destination.id == R.id.register) {
                bottomNavBar.visibility = View.GONE
            }else {
                bottomNavBar.visibility = View.VISIBLE
            }

        }

    }


    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

}

