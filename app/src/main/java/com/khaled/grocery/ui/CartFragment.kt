package com.khaled.grocery.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khaled.grocery.R
import com.khaled.grocery.databinding.FragmentCartBinding
import com.khaled.grocery.databinding.FragmentHomeBinding
import com.khaled.grocery.model.State
import com.khaled.grocery.ui.adapter.CartAdapter
import com.khaled.grocery.ui.adapter.ProductAdapter
import com.khaled.grocery.ui.view_model.CartViewModel
import com.khaled.grocery.ui.view_model.MainViewModel

class CartFragment : Fragment() {

    companion object {
        fun newInstance() = CartFragment()
    }

    private lateinit var binding: FragmentCartBinding

    private lateinit var viewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity())[CartViewModel::class.java]
        //binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = CartAdapter(mutableListOf())
        binding.productRecycler.adapter = adapter
        viewModel.cartItems.observe(viewLifecycleOwner) { state ->
            if (state is State.Success){
                adapter.setItems(state.toData()!!)
            }

        }
        return binding.root
    }
}
