package com.khaled.grocery.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.khaled.grocery.databinding.ActivityLoginBinding
import com.khaled.grocery.domain.repository.LoginResult
import com.khaled.grocery.ui.view_model.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(email, password)
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        lifecycleScope.launch {
            viewModel.loginResult.collectLatest { result ->
                result?.onSuccess { response ->
                    Toast.makeText(this@LoginActivity, "Login Successful! Token: ${response.data.token}", Toast.LENGTH_LONG).show()
                }?.onFailure { error ->
                    Toast.makeText(this@LoginActivity, "Login Failed: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}