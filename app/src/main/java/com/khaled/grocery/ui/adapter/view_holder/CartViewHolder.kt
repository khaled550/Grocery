package com.khaled.grocery.ui.adapter.view_holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.khaled.grocery.databinding.CartItemBinding

class CartViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val binding = CartItemBinding.bind(itemView)
}