package com.khaled.grocery.ui

import SignUpRequest
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.khaled.grocery.R
import com.khaled.grocery.databinding.FragmentSignUpBinding
import com.khaled.grocery.model.State
import com.khaled.grocery.ui.view_model.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        binding.signUpButton.setOnClickListener {
            val signUpRequest = validateInputAndBuildRequest()
            if (signUpRequest != null) {
                authViewModel.signUp(signUpRequest)
                viewLifecycleOwner.lifecycleScope.launch {
                    authViewModel.signupState.collect { state ->
                        when (state) {
                            is State.Loading -> {
                                // Show loading state
                                binding.progressBar.visibility = View.VISIBLE
                            }
                            is State.Success -> {
                                // Handle success, e.g., navigate to another screen
                                binding.progressBar.visibility = View.GONE
                                Toast.makeText(context, state.data.message, Toast.LENGTH_SHORT).show()
                            }
                            is State.Fail -> {
                                // Handle error
                                binding.progressBar.visibility = View.GONE
                                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }

private fun validateInputAndBuildRequest(): SignUpRequest? {
        val fullName = binding.name.text.toString().trim()
        val email = binding.email.text.toString().trim()
        val password = binding.password.text.toString().trim()
        val phone = binding.phone.text.toString().trim()

    if (fullName.isBlank()) {
        Toast.makeText(context, getString(R.string.name_required), Toast.LENGTH_SHORT).show()
        return null
    }

    if (email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        Toast.makeText(context, getString(R.string.email_required), Toast.LENGTH_SHORT).show()
        return null
    }

    if (phone.isBlank() || phone.length < 7) {
        Toast.makeText(context, getString(R.string.phone_required), Toast.LENGTH_SHORT).show()
        return null
    }

    if (password.isBlank() || password.length < 6) {
        Toast.makeText(context, getString(R.string.password_6_characters), Toast.LENGTH_SHORT).show()
        return null
    }

    return SignUpRequest(
        name = fullName.trim(),
        email = email.trim(),
        phone = phone.trim(),
        password = password
    )
    }
}