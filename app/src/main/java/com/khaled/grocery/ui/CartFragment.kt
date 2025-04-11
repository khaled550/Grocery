package com.khaled.grocery.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.khaled.grocery.R
import com.khaled.grocery.databinding.FragmentCartBinding
import com.khaled.grocery.model.State
import com.khaled.grocery.ui.adapter.CartAdapter
import com.khaled.grocery.ui.view_model.CartViewModel

class CartFragment : Fragment() {

    companion object {
        fun newInstance() = CartFragment()
    }

    private lateinit var binding: FragmentCartBinding
    private val viewModel: CartViewModel by activityViewModels()

    private val cartAdapter = CartAdapter(
        onUpdate = { item, onComplete -> viewModel.updateCartItem(item, onComplete) },
        onDelete = { itemId, onComplete -> viewModel.deleteCartItem(itemId, onComplete) }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.cart)
        binding = FragmentCartBinding.inflate(layoutInflater)
        binding = FragmentCartBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.checkoutButton.setOnClickListener {
            findNavController().navigate(R.id.action_cartFragment_to_checkoutFragment)
        }
        setupRecyclerView()

        observeCartItems()

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.cartRecyclerView.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setupSwipeToDelete()
        }
    }

    private fun observeCartItems() {
        viewModel.cartState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.emptyCartLayout.visibility = View.GONE
                }
                is State.Success -> {
                    binding.progressBar.visibility = View.GONE
                    if (State.Success(state).toData()?.data?.data?.cartItems.isNullOrEmpty()) {
                        binding.emptyCartLayout.visibility = View.VISIBLE
                        binding.checkoutButton.visibility = View.GONE
                    } else {
                        binding.emptyCartLayout.visibility = View.GONE
                        binding.checkoutButton.visibility = View.VISIBLE
                    }
                    cartAdapter.submitList(state.data?.data?.cartItems)
                }
                is State.Fail -> {
                    binding.progressBar.visibility = View.GONE
                    binding.emptyCartLayout.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), state.msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.loadCartItems()
    }

    private fun setupSwipeToDelete() {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false // No move action needed
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val item = cartAdapter.currentList[position].id // Get the item being swiped

                viewModel.deleteCartItem(item) {
                    /*// Optional: Show Snackbar to Undo
                    Snackbar.make(requireView(), "${item.product?.name} removed", Snackbar.LENGTH_LONG)
                        .setAction("Undo") {
                            //viewModel.addCartItem(item) // Restore item on undo
                        }.show()*/
                }
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.cartRecyclerView) // Attach to RecyclerView
    }
}
