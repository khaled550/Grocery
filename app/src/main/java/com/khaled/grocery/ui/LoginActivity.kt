package com.khaled.grocery.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.khaled.grocery.databinding.ActivityLoginBinding
import com.khaled.grocery.domain.repository.LoginResult
import com.khaled.grocery.ui.view_model.LoginViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Collect login result flow from ViewModel
        MainScope().launch {
            viewModel.loginResult.collect { result ->
                when (result) {
                    is LoginResult.Success -> {
                        // Navigate to MainActivity on successful login
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    is LoginResult.Error -> {
                        // Show error message
                        Toast.makeText(this@LoginActivity, result.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // Handle login button click
        binding.loginButton.setOnClickListener {
            val username = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            // Call the ViewModel login function
            viewModel.login(username, password)
        }
    }
}