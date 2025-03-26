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
import com.khaled.grocery.databinding.FragmentAccountBinding
import com.khaled.grocery.ui.view_model.AccountViewModel
import com.khaled.grocery.ui.view_model.AuthViewModel

class AccountFragment : Fragment() {

    companion object {
        fun newInstance() = AccountFragment()
    }

    private val authViewModel: AuthViewModel by activityViewModels()

    private lateinit var viewModel: AccountViewModel

    private lateinit var binding: FragmentAccountBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[AccountViewModel::class.java]
        binding.lifecycleOwner = viewLifecycleOwner

        binding.logoutButton.setOnClickListener {
            showLogoutConfirmationDialog()
        }

        return binding.root
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