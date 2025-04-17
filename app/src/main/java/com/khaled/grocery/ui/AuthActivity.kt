package com.khaled.grocery.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.khaled.grocery.R
import com.khaled.grocery.databinding.ActivityLoginBinding
import com.khaled.grocery.ui.view_model.AuthViewModel
import com.khaled.grocery.utils.UserPreferences
import dagger.hilt.android.AndroidEntryPoint

import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this

        /*val navController = findNavController(R.id.nav_host_fragment)
        binding.authNavHostFragment.setupWithNavController(navController)*/

    }
}