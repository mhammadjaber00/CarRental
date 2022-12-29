package com.project.carrental.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.project.carrental.databinding.ActivityMainBinding
import com.project.carrental.presentation.admin.AdminMainActivity
import com.project.carrental.presentation.customer.CustomerMainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCustomer.setOnClickListener {
            startActivity(Intent(this, CustomerMainActivity::class.java))
        }
        binding.btnAdmin.setOnClickListener {
            startActivity(Intent(this, AdminMainActivity::class.java))
        }
    }
}