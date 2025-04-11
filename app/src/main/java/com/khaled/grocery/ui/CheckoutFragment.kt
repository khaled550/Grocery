package com.khaled.grocery.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.khaled.grocery.R
import com.khaled.grocery.databinding.FragmentCheckoutBinding
import com.khaled.grocery.model.PlaceOrderItem
import com.khaled.grocery.model.State
import com.khaled.grocery.ui.adapter.CartAdapter
import com.khaled.grocery.ui.adapter.CheckoutCartAdapter
import com.khaled.grocery.ui.view_model.CartViewModel
import com.khaled.grocery.ui.view_model.CheckoutViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class CheckoutFragment @Inject constructor() : Fragment() {

    private lateinit var binding: FragmentCheckoutBinding
    private val viewModel: CheckoutViewModel by activityViewModels()
    private val checkoutCartAdapter = CheckoutCartAdapter()

    private lateinit var placeOrderItem : PlaceOrderItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckoutBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        (activity as MainActivity).supportActionBar?.title = getString(R.string.checkout)

        placeOrder()

        setupRecyclerView()

        return binding.root
    }

    private fun placeOrder() {
        binding.btnCheckout.setOnClickListener {
            viewModel.checkout()
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.checkoutState.collectLatest { state ->
                    when (state) {
                        is State.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is State.Success -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(requireContext(),
                                getString(R.string.order_placed_successfully), Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_checkoutFragment_to_cartFragment)
                        }
                        is State.Fail -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(requireContext(), state.msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.cartItemsRecycler.apply {
            adapter = checkoutCartAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        //checkoutCartAdapter.submitList(cartViewModel.cartState.value?.toData()?.data?.cartItems)
    }
}
