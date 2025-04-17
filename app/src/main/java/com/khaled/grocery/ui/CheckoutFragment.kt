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
import com.khaled.grocery.model.State
import com.khaled.grocery.ui.adapter.CheckoutCartAdapter
import com.khaled.grocery.ui.view_model.CheckoutViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

class CheckoutFragment @Inject constructor() : Fragment() {

    private lateinit var binding: FragmentCheckoutBinding
    private val viewModel: CheckoutViewModel by activityViewModels()
    private val checkoutCartAdapter = CheckoutCartAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckoutBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        (activity as MainActivity).supportActionBar?.title = getString(R.string.checkout)

        placeOrder()
        observeCartItems()

        return binding.root
    }

    private fun observeCartItems() {
        setupRecyclerView()
        viewLifecycleOwner.lifecycleScope.launch{
            viewModel.cartData.combine(viewModel.address){
                cartState, addressState ->
                Pair(cartState, addressState)
            }.collectLatest { (cartState, addressState) ->
                when (cartState) {
                    is State.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is State.Success -> {
                        binding.progressBar.visibility = View.GONE
                        if (!cartState.data.data?.cartItems.isNullOrEmpty()) {
                            checkoutCartAdapter.submitList(cartState.data.data?.cartItems)
                        }
                    }
                    is State.Fail -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), cartState.message, Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }
                }

                when (addressState) {
                    is State.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is State.Success -> {
                        binding.addressText.text = viewModel.address.value.toData()?.name
                    }
                    is State.Fail -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), addressState.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun placeOrder() {
        binding.btnCheckout.setOnClickListener {
            binding.paymentMethodGroup.id = when (binding.paymentMethodGroup.checkedRadioButtonId) {
                R.id.cash -> 1
                R.id.online -> 2
                else -> 0
            }
            val paymentMethod = binding.paymentMethodGroup.id
            if (paymentMethod == 0) {
                Toast.makeText(requireContext(),
                    getString(R.string.select_payment_method), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.checkout(paymentMethod)
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.checkoutState.collectLatest { state ->
                    when (state) {
                        is State.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is State.Success -> {

                            binding.progressBar.visibility = View.GONE
                            if (state.data.status!!) {
                                Toast.makeText(requireContext(), state.data.message, Toast.LENGTH_SHORT).show()
                                findNavController().popBackStack()
                            } else {
                                Toast.makeText(requireContext(), state.data.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                        is State.Fail -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
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
            setHasFixedSize(true)
        }
    }
}
