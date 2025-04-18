package com.khaled.grocery.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.khaled.grocery.R
import com.khaled.grocery.databinding.FragmentHomeBinding
import com.khaled.grocery.model.Product
import com.khaled.grocery.model.State
import com.khaled.grocery.ui.adapter.ProductAdapter
import com.khaled.grocery.ui.adapter.ProductTouchListener
import com.khaled.grocery.ui.view_model.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), ProductTouchListener {

    val viewModel: MainViewModel by viewModels()

    private lateinit var binding: FragmentHomeBinding

    private lateinit var productAdapter: ProductAdapter

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
        productAdapter = ProductAdapter(this@HomeFragment)
        binding.recyclerView.adapter = productAdapter
        binding.searchEditText.setOnEditorActionListener{ _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.searchEditText.text.toString()
                productAdapter.filter(query)
                true
            } else {
                false
            }
        }
    }

    private fun observeHomeItems() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.productList.collect() { state ->
                when (state) {
                    is State.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is State.Success -> {
                        binding.progressBar.isVisible = false
                        binding.recyclerView.adapter?.let { adapter ->
                            if (adapter is ProductAdapter) {
                                adapter.setFullList(state.toData()!!.data!!.products!!)
                            }
                        }
                    }
                    is State.Fail -> {
                        binding.progressBar.isVisible = false
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onClickItem(product: Product) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(product.id!!)
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.title = getString(R.string.app_name)
        viewModel.getHomeData()
    }
}
