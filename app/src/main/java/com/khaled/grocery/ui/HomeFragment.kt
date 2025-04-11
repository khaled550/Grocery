package com.khaled.grocery.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.khaled.grocery.databinding.FragmentHomeBinding
import com.khaled.grocery.model.Product
import com.khaled.grocery.model.State
import com.khaled.grocery.ui.adapter.ProductAdapter
import com.khaled.grocery.ui.adapter.ProductTouchListener
import com.khaled.grocery.ui.view_model.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), ProductTouchListener {

    val viewModel: MainViewModel by viewModels()

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setupRecyclerView()
        observeHomeItems()

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            adapter = ProductAdapter(this@HomeFragment).apply {
                setHasStableIds(true)
            }
        }
    }

    private fun observeHomeItems() {
        viewModel.homeProducts.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Loading -> binding.progressBar.isVisible = true
                is State.Success -> {
                    binding.progressBar.isVisible = false
                    binding.recyclerView.adapter?.let { adapter ->
                        if (adapter is ProductAdapter) {
                            adapter.submitList(state.toData()?.data?.products)
                        }
                    }
                }
                is State.Fail -> {
                    binding.progressBar.isVisible = false
                    // Handle error state
                }
            }
        }
}
    override fun onClickItem(product: Product) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(product.id!!)
        findNavController().navigate(action)
    }
}
