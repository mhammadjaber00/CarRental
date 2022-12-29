package com.project.carrental.presentation.customer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.carrental.R
import com.project.carrental.databinding.ActivityMainCustomerBinding

class CustomerMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainCustomerBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigation()

    }

    private fun setupBottomNavigation() {
        bottomNavigationView = binding.bottomNav
        bottomNavigationView.itemIconTintList = null

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_home_container) as NavHostFragment
        navController = navHostFragment.navController

        bottomNavigationView.setupWithNavController(navController)
    }
}