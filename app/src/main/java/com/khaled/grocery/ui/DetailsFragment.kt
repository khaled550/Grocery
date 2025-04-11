package com.khaled.grocery.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.khaled.grocery.R
import com.khaled.grocery.databinding.FragmentDetailsBinding
import com.khaled.grocery.model.DataResponse
import com.khaled.grocery.model.Product
import com.khaled.grocery.model.State
import com.khaled.grocery.ui.view_model.DetailsViewModel

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

        loadProductDetails()

        return binding.root
    }

    private fun loadProductDetails() {
        val productId = args.itemId
        viewModel.getProductDetails(productId)
        viewModel.productDetails.observe(viewLifecycleOwner) { state ->
            //viewModel.checkCartItem()
            updateUI(state)
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
                binding.addToCartButton.visibility = View.VISIBLE
                binding.addToCartButton.isEnabled = true
                // Update UI with product details
                val product = state.data?.data
                showData(product)
                setUpAddToCartBtn()
                checkCartItem()
            }

            is State.Fail -> {
                // Handle error
                //checkCartItem()
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), state.msg, Toast.LENGTH_SHORT).show()
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
            viewModel.addOrRemoveCartItem(productId) {
                // Handle completion
                loadProductDetails()
            }
        }
    }

    private fun checkCartItem() {
        val isInCart = viewModel.checkCartItem()
        if (isInCart) {
            binding.addToCartButton.text = getString(R.string.remove_cart_item)
        } else {
            binding.addToCartButton.text = getString(R.string.add_to_cart)
        }
    }
}
