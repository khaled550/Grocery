package com.khaled.grocery.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.khaled.grocery.databinding.CartItemBinding
import com.khaled.grocery.model.CartItem
import com.khaled.grocery.ui.view_model.CartViewModel

class CartAdapter(private val viewModel: CartViewModel) :
    ListAdapter<CartItem, CartAdapter.CartViewHolder>(CartDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, viewModel)
    }

    class CartViewHolder(private val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartItem, viewModel: CartViewModel) {
            binding.item = item
            binding.executePendingBindings()

            binding.addBtn.setOnClickListener {
                viewModel.updateQuantity(item.id, item.quantity?.plus(1) ?: 1)
            }

            binding.removeBtn.setOnClickListener {
                if (item.quantity!! > 1) {
                    viewModel.updateQuantity(item.id, item.quantity?.minus(1) ?: 1)
                } else {
                    viewModel.removeItem(item.id)
                }
            }
        }
    }

    class CartDiffCallback : DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem) = oldItem == newItem
    }
}
