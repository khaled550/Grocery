package com.khaled.grocery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.khaled.grocery.databinding.ActivityMainBinding
import com.khaled.grocery.model.State
import com.khaled.grocery.ui.view_model.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //binding.viewModel = mainViewModel
        setup()
    }

    private fun setup() {
        mainViewModel.homeProducts.observe(this) {
            if (it is State.Loading)
                Log.d("getHomeData: ", "Loading...")
            if (it is State.Success)
                Log.d("getHomeData: ", it.data?.data?.products?.size.toString())
        }
    }
}