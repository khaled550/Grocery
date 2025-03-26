package com.khaled.grocery.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.khaled.grocery.databinding.ActivityLoginBinding
import com.khaled.grocery.domain.repository.MyResult
import com.khaled.grocery.ui.view_model.AuthViewModel
import com.khaled.grocery.utils.UserPreferences
import dagger.hilt.android.AndroidEntryPoint

import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val authViewModel: AuthViewModel by viewModels()

    @Inject
    lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        observeLoginState()
        loadRememberedUser()
    }

    private fun setupListeners() {
        binding.loginButton.setOnClickListener {
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                authViewModel.login(email, password)
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeLoginState() {
        lifecycleScope.launch {
            authViewModel.loginState.collect { result ->
                when (result) {
                    is MyResult.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is MyResult.Success -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                        navigateToHome()
                    }
                    is MyResult.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@LoginActivity, "Error: ${result.message}", Toast.LENGTH_SHORT).show()
                    }
                    null -> Unit
                }
            }
        }
    }

    private fun navigateToHome() {
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        finish()
    }

    private fun loadRememberedUser() {
        lifecycleScope.launch {
            userPreferences.authToken.collect { token ->
                if (!token.isNullOrEmpty()) {
                    navigateToHome()
                }
            }
        }
    }
}