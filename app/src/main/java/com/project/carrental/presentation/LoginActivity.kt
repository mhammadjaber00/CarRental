package com.project.carrental.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.project.carrental.R
import com.project.carrental.databinding.ActivityLoginBinding
import com.project.carrental.presentation.admin.AdminMainActivity
import com.project.carrental.presentation.customer.CustomerMainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var isAdmin = true

    override fun onCreate(savedInstanceState : Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            rgUserSelection.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.rb_admin -> {
                        isAdmin = true
                    }
                    R.id.rb_customer -> {
                        isAdmin = false
                    }
                }
            }
            btnSignIn.setOnClickListener {
                if (isAdmin) {
                    startActivity(Intent(this@LoginActivity, AdminMainActivity::class.java))
                    finishAffinity()
                } else {
                    startActivity(Intent(this@LoginActivity, CustomerMainActivity::class.java))
                    finishAffinity()
                }
            }
        }
    }
}