package com.khaled.grocery.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.khaled.grocery.databinding.FragmentOrderDetailsBinding
import com.khaled.grocery.model.CartItem
import com.khaled.grocery.model.OrderProductItem
import com.khaled.grocery.model.Product
import com.khaled.grocery.model.State
import com.khaled.grocery.ui.adapter.CheckoutCartAdapter
import com.khaled.grocery.ui.view_model.OrderDetailsViewModel
import kotlinx.coroutines.launch

class OrderDetailsFragment : Fragment() {

    private val args: OrderDetailsFragmentArgs by navArgs()
    private val viewModel: OrderDetailsViewModel by activityViewModels()
    private lateinit var binding: FragmentOrderDetailsBinding
    private lateinit var checkoutCartAdapter: CheckoutCartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeOrderDetails()
    }

    private fun setupRecyclerView() {
        checkoutCartAdapter = CheckoutCartAdapter()
        binding.itemsRecyclerView.apply {
            adapter = checkoutCartAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun observeOrderDetails() {
        viewModel.loadOrderDetails(args.orderId)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.orderDetails.collect { state ->
                when (state) {
                    is State.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is State.Success -> {
                        binding.orderDetailsLayout.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        checkoutCartAdapter.submitList(convertProductListToCartItems(state.data.data?.products ?: emptyList()))
                        updateUI()
                    }
                    is State.Fail -> {
                        binding.progressBar.visibility = View.GONE
                        // Handle error
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    private fun updateUI() {
        val orderDetails = viewModel.orderDetails.value.toData()?.data!!
        binding.orderNumberText.text = viewModel.orderDetails.value.toData()?.data?.orderId.toString()
        binding.costText.text = String.format("$%,.0f", orderDetails.cost)
        val fullAddress = buildString {
            append(orderDetails.address?.name)
            append(", ")
            append(orderDetails.address?.city)
            append(", ")
            append(orderDetails.address?.region)
            append(", ")
            append(orderDetails.address?.details)
            if (!orderDetails.address?.notes.isNullOrEmpty()) {
                append(", ")
                append(orderDetails.address?.notes)
            }
        }
        binding.addressText.text = fullAddress
        binding.paymentMethodText.text = orderDetails.paymentMethod
        binding.vatText.text = String.format("%d%%", orderDetails.vat.toInt())
        binding.discountText.text = String.format("%d%%", orderDetails.discount)
        binding.totalText.text = String.format("$%,.0f", orderDetails.total)
    }

    private fun convertProductListToCartItems(products: List<OrderProductItem>): List<CartItem> {
        val cartItems = mutableListOf<CartItem>()
        var cartItem: CartItem
        for (product in products) {
            cartItem = CartItem(
                quantity = product.quantity,
                product = Product(
                    id = product.id,
                    name = product.name,
                    price = product.price,
                    image = product.image,
                ),
            )
            cartItems.add(cartItem)
        }
        return cartItems
    }
}
