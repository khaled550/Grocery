package com.khaled.grocery.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.khaled.grocery.databinding.FragmentCartBinding
import com.khaled.grocery.model.State
import com.khaled.grocery.ui.adapter.CartAdapter
import com.khaled.grocery.ui.view_model.CartViewModel
import kotlinx.coroutines.launch

class CartFragment : Fragment() {

    companion object {
        fun newInstance() = CartFragment()
    }

    private lateinit var binding: FragmentCartBinding
    private val viewModel: CartViewModel by activityViewModels()
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(layoutInflater)
        binding = FragmentCartBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        cartAdapter = CartAdapter(viewModel)
        setupRecyclerView()

        observeCartItems()
        return binding.root
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(viewModel)
        binding.cartRecyclerView.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeCartItems() {
        lifecycleScope.launch {
            viewModel.cartItems.collect { items ->
                cartAdapter.submitList(items)
            }
        }
    }
}
