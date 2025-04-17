package com.khaled.grocery.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.khaled.grocery.R
import com.khaled.grocery.databinding.FragmentOrderBinding
import com.khaled.grocery.model.State
import com.khaled.grocery.ui.adapter.OrderAdapter
import com.khaled.grocery.ui.adapter.OrderTouchListener
import com.khaled.grocery.ui.view_model.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrdersFragment : Fragment(), OrderTouchListener {

    private lateinit var binding: FragmentOrderBinding

    private val viewModel: OrderViewModel by viewModels()
    private lateinit var orderAdapter: OrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.my_orders)
        binding = FragmentOrderBinding.inflate(layoutInflater)
        binding.lifecycleOwner = viewLifecycleOwner

        observeAddresses()

        return binding.root
    }

    private fun observeAddresses() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.orders.collect { state ->
                when (state) {
                    is State.Loading -> {
                        // Show loading indicator
                        setupRecyclerView()
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is State.Success -> {
                        // Update UI with addresses
                        if (state.data.data?.orderSummaryItems!!.isEmpty()) {
                            binding.noOrdersLayout.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                            binding.progressBar.visibility = View.GONE
                        } else {
                            binding.progressBar.visibility = View.GONE
                            val orders = state.data.data.orderSummaryItems
                            orderAdapter.submitList(orders)
                            Log.i("OrdersFragment", "orders: $orders")
                        }
                    }
                    is State.Fail -> {
                        // Handle error
                        binding.progressBar.visibility = View.GONE
                        binding.noOrdersLayout.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        orderAdapter = OrderAdapter(this)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = orderAdapter
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerView.adapter = null
    }

    override fun onResume() {
        super.onResume()
        //(activity as AppCompatActivity).supportActionBar?.title = getString(R.string.my_orders)
        viewModel.getAllOrders()
    }

    override fun onClickItem(orderId: Int) {
        val action = OrdersFragmentDirections.actionOrderFragmentToOrderDetailsFragment(orderId)
        findNavController().navigate(action)
    }
}
