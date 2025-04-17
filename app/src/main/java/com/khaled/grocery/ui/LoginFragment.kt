package com.khaled.grocery.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.khaled.grocery.R
import com.khaled.grocery.databinding.FragmentLoginBinding
import com.khaled.grocery.model.State
import com.khaled.grocery.ui.view_model.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(){

    private lateinit var binding: FragmentLoginBinding
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeLoginState()
        loadRememberedUser()
    }

    private fun setupUI() {
        binding.loginButton.setOnClickListener {
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                authViewModel.login(email, password)
            } else {
                Toast.makeText(activity,
                    getString(R.string.enter_email_password), Toast.LENGTH_SHORT).show()
            }
        }
        binding.signupText.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }
    }

    private fun observeLoginState() {
        viewLifecycleOwner.lifecycleScope.launch {
            authViewModel.loginState.collect { result ->
                when (result) {
                    is State.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is State.Success -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(activity,
                            getString(R.string.login_successful), Toast.LENGTH_SHORT).show()
                        navigateToHome()
                    }
                    is State.Fail -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(activity, "Error: ${result.message}", Toast.LENGTH_SHORT).show()
                    }
                    null -> Unit
                }
            }
        }
    }

    private fun navigateToHome() {
        startActivity(Intent(activity, MainActivity::class.java))
        activity?.finish()
    }

    private fun loadRememberedUser() {
        lifecycleScope.launch {
            authViewModel.authToken.collect { token ->
                if (!token.isNullOrEmpty()) {
                    navigateToHome()
                }
            }
        }
    }
}

