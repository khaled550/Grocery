package com.khaled.grocery.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khaled.grocery.R
import com.khaled.grocery.model.CartModel
import com.khaled.grocery.ui.adapter.view_holder.CartViewHolder

class CartAdapter(private var cartItems: List<CartModel>) : RecyclerView.Adapter<CartViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return CartViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val currentItem = cartItems[position]

        holder.binding.item = currentItem
    }

    fun setItems(cartItems: List<CartModel>) {
        this.cartItems = cartItems
        notifyDataSetChanged()
    }
}