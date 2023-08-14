package com.khaled.grocery.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.khaled.grocery.databinding.FragmentHomeBinding
import com.khaled.grocery.model.State
import com.khaled.grocery.ui.adapter.ProductAdapter
import com.khaled.grocery.ui.view_model.MainViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private var mainViewModel = MainViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        binding.viewModel = mainViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = ProductAdapter(mutableListOf(), mainViewModel)
        binding.productRecycler.adapter = adapter
        mainViewModel.homeProducts.observe(viewLifecycleOwner) { state ->
            if (state is State.Success){
                adapter.setItems(state.toData()!!)
            }

        }
        return binding.root
    }

}
