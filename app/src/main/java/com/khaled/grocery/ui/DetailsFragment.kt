package com.khaled.grocery.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.khaled.grocery.R
import com.khaled.grocery.databinding.FragmentDetailsBinding
import com.khaled.grocery.model.DataResponse
import com.khaled.grocery.model.Product
import com.khaled.grocery.model.State
import com.khaled.grocery.ui.view_model.DetailsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailsFragment : Fragment() {

    private val args: DetailsFragmentArgs by navArgs()

    private lateinit var binding: FragmentDetailsBinding

    val viewModel: DetailsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //(activity as AppCompatActivity).supportActionBar?.title = getString(R.string.my_addresses)
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadProductDetails()
        setUpAddToCartBtn()
    }

    private fun loadProductDetails() {
        val productId = args.itemId
        viewModel.getProductDetails(productId)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.productDetails.collect { state ->
                updateUI(state)
            }
        }
    }

    private fun updateUI(state: State<DataResponse<Product>?>?) {
        if (state != null) {
            when (state) {
            is State.Loading -> {
                // Show loading indicator
                binding.progressBar.visibility = View.VISIBLE
                binding.addToCartButton.isEnabled = false
            }
            is State.Success -> {
                binding.progressBar.visibility = View.GONE
                binding.addToCartButton.visibility = View.VISIBLE
                binding.addToCartButton.isEnabled = true
                val inCart = state.data?.data?.inCart == true
                binding.addToCartButton.text = getString(
                    if (inCart) R.string.remove_cart_item else R.string.add_to_cart)
                // Update UI with product details
                val product = state.data?.data
                showData(product)
            }
            is State.Fail -> {
                binding.progressBar.visibility = View.GONE
                binding.addToCartButton.isEnabled = true
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
            }
        }
        }
    }

    private fun showData(product: Product?) {
        binding.progressBar.visibility = View.GONE
        binding.productName.text = product?.name
        binding.productPrice.text = product?.price.toString()
        binding.productDescription.text = product?.description
        Glide.with(this)
            .load(product?.images?.get(0))
            .centerCrop()
            .transform(RoundedCorners(24))
            .into(binding.productImage)
    }

    private fun setUpAddToCartBtn(){
        val productId = args.itemId
        binding.addToCartButton.setOnClickListener {
            viewModel.toggleCartBtn(productId)
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.cartResponse.collectLatest { state ->
                    when (state) {
                        is State.Loading -> {
                            binding.addToCartButton.isEnabled = false
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is State.Success -> {
                            binding.addToCartButton.isEnabled = true
                            binding.progressBar.visibility = View.GONE
                        }
                        is State.Fail -> {
                            binding.addToCartButton.isEnabled = true
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}
