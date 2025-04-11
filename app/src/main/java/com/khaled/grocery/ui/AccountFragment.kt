package com.khaled.grocery.ui

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.khaled.grocery.databinding.FragmentAccountBinding
import com.khaled.grocery.model.State
import com.khaled.grocery.ui.view_model.AccountViewModel
import com.khaled.grocery.ui.view_model.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class AccountFragment : Fragment() {

    companion object {
        fun newInstance() = AccountFragment()
    }

    private val authViewModel: AuthViewModel by activityViewModels()
    private val viewModel: AccountViewModel by viewModels()

    private lateinit var binding: FragmentAccountBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(layoutInflater)
        binding.lifecycleOwner = viewLifecycleOwner

        observeUserData()
        setupOptions()

        return binding.root
    }

    private fun observeUserData() {
        viewModel.userData.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is State.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val user = it.toData()?.data
                    if (user != null) {
                        binding.userName.text = user.name
                        binding.userEmail.text = user.email
                        binding.userPhone.text = user.phone
                    }
                }
                is State.Fail -> {
                    binding.progressBar.visibility = View.GONE
                    // Handle error state
                }
            }
        }
    }

    private fun setupOptions() {
        binding.optionLogout.setOnClickListener {
            showLogoutConfirmationDialog()
        }
        binding.optionOrders.setOnClickListener {
            val action = AccountFragmentDirections.actionAccountFragmentToOrderFragment()
            findNavController().navigate(action)
        }
        binding.optionSettings.setOnClickListener {
        }
        binding.optionAddresses.setOnClickListener {
            val action = AccountFragmentDirections.actionAccountFragmentToAddressFragment()
            findNavController().navigate(action)
        }
    }

    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { _, _ ->
                authViewModel.logout()
                navigateToLogin()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun navigateToLogin() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}