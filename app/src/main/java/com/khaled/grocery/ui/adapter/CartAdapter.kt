package com.khaled.grocery.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.khaled.grocery.R
import com.khaled.grocery.databinding.CartItemBinding
import com.khaled.grocery.model.CartItem

class CartAdapter(private val onUpdate: (CartItem, () -> Unit) -> Unit,
                  private val onDelete: (Int, () -> Unit) -> Unit) :
    ListAdapter<CartItem, CartAdapter.CartViewHolder>(CartDiffCallback()) {

    private val cartItems = mutableListOf<CartItem>()
    private val updatingPositions = mutableSetOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class CartViewHolder(private val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartItem) {
            binding.item = item
            binding.executePendingBindings()

            val isUpdating = updatingPositions.contains(position)
            binding.addBtn.isEnabled = !isUpdating
            binding.removeBtn.isEnabled = !isUpdating
            binding.quantityText.text = item.quantity.toString()
            Glide.with(binding.productImg.context)
                .load(item.product?.image!!)
                .placeholder(R.drawable.product_placeholder)
                .centerCrop()
                .transform(RoundedCorners(24))
                .into(binding.productImg)

            binding.addBtn.setOnClickListener {
                if (!isUpdating) {
                    disableButtons(position)
                    val updatedItem = item.copy(quantity = item.quantity?.plus(1))
                    onUpdate(updatedItem) { enableButtons(position) }
                    updateUI(updatedItem, adapterPosition) // Update UI
                }
            }

            binding.removeBtn.setOnClickListener {
                if (!isUpdating && item.quantity!! > 1) {
                    disableButtons(position)
                    val updatedItem = item.copy(quantity = item.quantity!! - 1)
                    onUpdate(updatedItem) { enableButtons(position) }
                    updateUI(updatedItem, adapterPosition) // Update UI
            }
                else {
                    //Remove item when quantity reaches 0
                    onDelete(item.id) { enableButtons(adapterPosition) }
                }
        }
    }

    private fun updateUI(updatedItem: CartItem, position: Int) {
        if (position in 0 until cartItems.size) {
            cartItems[position] = updatedItem
            notifyItemChanged(position)
        }
    }

    private fun disableButtons(position: Int) {
        updatingPositions.add(position)
        notifyItemChanged(position)
    }

    private fun enableButtons(position: Int) {
        updatingPositions.remove(position)
        notifyItemChanged(position)
    }
}
    }


class CartDiffCallback : DiffUtil.ItemCallback<CartItem>() {
    override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
        return oldItem == newItem // Checks all properties
    }
}
