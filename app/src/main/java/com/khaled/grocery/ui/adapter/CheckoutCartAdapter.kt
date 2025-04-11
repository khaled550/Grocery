package com.khaled.grocery.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.khaled.grocery.R
import com.khaled.grocery.databinding.ItemCheckoutProductBinding
import com.khaled.grocery.model.CartItem

class CheckoutCartAdapter() :
ListAdapter<CartItem, CheckoutCartAdapter.CheckoutCartViewHolder>(CartDiffCallback()) {

    private val cartItems = mutableListOf<CartItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutCartViewHolder {
        val binding = ItemCheckoutProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CheckoutCartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CheckoutCartViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class CheckoutCartViewHolder(private val binding: ItemCheckoutProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartItem) {
            binding.item = item
            binding.executePendingBindings()

            var totalPrice = (item.quantity?.times(item.product?.price!!))!!.times(item.quantity!!)
            binding.textPrice.text = String.format("$%.2f", totalPrice)
            Glide.with(binding.productImg.context)
                .load(item.product?.image!!)
                .placeholder(R.drawable.product_placeholder)
                .centerCrop()
                .transform(RoundedCorners(24))
                .into(binding.productImg)
        }
    }

    private fun updateUI(item: CartItem, position: Int) {
        cartItems[position] = item
        notifyItemChanged(position)
    }
}
