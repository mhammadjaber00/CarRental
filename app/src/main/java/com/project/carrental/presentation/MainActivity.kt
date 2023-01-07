package com.project.carrental.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.project.carrental.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, LoginActivity::class.java)
        binding.btnCustomer.setOnClickListener {
            intent.putExtra("isAdmin", false)
            startActivity(intent)
        }
        binding.btnAdmin.setOnClickListener {
            intent.putExtra("isAdmin", true)
            startActivity(intent)
        }
    }
}