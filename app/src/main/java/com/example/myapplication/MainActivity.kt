package com.example.myapplication // Your main activity's package

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.databinding.ActivityMainBinding
import androidx.navigation.ui.NavigationUI // Import this

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_profile
                // Add your other top-level destination IDs from the bottom nav menu here
            )
        )
        binding.navView.setupWithNavController(navController)

        // Default setup:
        // navView.setupWithNavController(navController) // We will replace this with custom handling

        // **Custom Handling for BottomNavigationView Item Selection**
        navView.setOnItemSelectedListener { item ->
            NavigationUI.onNavDestinationSelected(item, navController)
            // This part is crucial for popping the back stack:
            navController.popBackStack(item.itemId, false) // `false` means don't pop if already at start destination. Change to `true` to always pop and re-create.
            // Using `true` (inclusive) for popUpTo might be closer to what you want
            // to "reset" the tab. Let's try that.

            // A more robust way to ensure you go to the start destination of the tab and save/restore state:
            // This is the recommended way from official docs for this behavior.
            val builder = NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setRestoreState(true) // Restore state when reselecting a previously selected item
                .setPopUpTo(navController.graph.startDestinationId, false) // Pop up to the start destination of the graph
                .setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim) // Optional animations
                .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
                .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
                .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim)

            // If the selected item is not the current destination, navigate.
            // Also, to ensure you are always going to the START destination of that tab:
            // Find the start destination of the graph associated with the selected menu item.
            // This is a bit more complex if your tabs have separate nested graphs.
            // For now, let's assume item.itemId directly corresponds to a fragment in your main graph.

            // The simplest approach to always go to the root of the selected tab:
            val currentGraph = navController.graph.findNode(item.itemId)
            if (currentGraph != null) {
                // If it's a new selection or we want to force going to the root
                val options = NavOptions.Builder()
                    .setLaunchSingleTop(true)
                    .setPopUpTo(item.itemId, true) // Pop up to and including the item itself, then navigate to it.
                    .build()
                navController.navigate(item.itemId, null, options)
                true
            } else {
                false
            }
        }

        // To handle reselection of the same tab (if you want it to pop to its start destination on reselect)
        navView.setOnItemReselectedListener { item ->
            // Pop to the start destination of the current tab's back stack
            navController.popBackStack(item.itemId, false) // Inclusive false here is probably better to avoid reloading if already at start
            // To always go to the start, you might navigate with popUpTo inclusive true.
            val options = NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setPopUpTo(item.itemId, true) // Pop up to and including the item itself, then navigate to it.
                .build()
            navController.navigate(item.itemId, null, options)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
