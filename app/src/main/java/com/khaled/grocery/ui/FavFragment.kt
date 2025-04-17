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
import com.khaled.grocery.R
import com.khaled.grocery.databinding.FragmentFavBinding
import com.khaled.grocery.model.FavData
import com.khaled.grocery.model.Product
import com.khaled.grocery.model.State
import com.khaled.grocery.ui.adapter.ProductAdapter
import com.khaled.grocery.ui.adapter.ProductTouchListener
import com.khaled.grocery.ui.view_model.FavViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavFragment : Fragment(), ProductTouchListener {

    private lateinit var binding: FragmentFavBinding
    private val viewModel: FavViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.fav)
        binding = FragmentFavBinding.inflate(layoutInflater)

        binding.lifecycleOwner = viewLifecycleOwner
        setupRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeFavItems()
    }

    private fun observeFavItems(){

        viewModel.favItems.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Loading -> {
                    // Handle loading state
                    binding.progressBar.visibility = View.VISIBLE
                }
                is State.Success -> {
                    // Handle success state
                    binding.progressBar.visibility = View.GONE
                    binding.favRecycler.adapter?.let { adapter ->
                        if (adapter is ProductAdapter) {
                            adapter.submitList(convertToProductList(state.toData()!!.data!!))
                        }
                    }
                }
                is State.Fail -> {
                    // Handle error state
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Error: ${state.toData()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.favRecycler.apply {
            adapter = ProductAdapter(this@FavFragment).apply {
                setHasStableIds(true)
            }
        }
    }

    private fun convertToProductList(favList: FavData): List<Product> {
        val productList = mutableListOf<Product>()
        for (item in favList.favlist!!) {
            val product = item.product!!
            productList.add(product)
        }
        return productList
    }

    override fun onClickItem(product: Product) {
        val action = FavFragmentDirections.actionFavFragmentToDetailsFragment(product.id!!)
        findNavController().navigate(action)
    }
}