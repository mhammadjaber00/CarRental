package com.project.carrental.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.carrental.R
import com.project.carrental.databinding.ActivityLoginBinding
import com.project.carrental.databinding.ActivityMainBinding
import com.project.carrental.presentation.admin.AdminMainActivity
import com.project.carrental.presentation.customer.CustomerMainActivity
import kotlin.properties.Delegates

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val isAdmin by lazy {
        intent.getBooleanExtra("isAdmin", true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSignIn.setOnClickListener {
            if (isAdmin) {
                startActivity(Intent(this, AdminMainActivity::class.java))
                finishAffinity()
            }
            else {
                startActivity(Intent(this, CustomerMainActivity::class.java))
                finishAffinity()
            }
        }
    }
}