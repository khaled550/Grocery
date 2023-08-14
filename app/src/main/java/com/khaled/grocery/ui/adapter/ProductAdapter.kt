package com.khaled.grocery.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khaled.grocery.R
import com.khaled.grocery.model.Product
import com.khaled.grocery.ui.adapter.view_holder.ProductViewHolder

class ProductAdapter(val productList: List<Product>, val listener: ProductTouchListener) : RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount() = productList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentProduct = productList[position]
        //holder.productNameText.text = currentProduct.name
        holder.binding.item = currentProduct
        holder.itemView.setOnClickListener {
            listener.onClickItem(currentProduct)
        }
    }
}

interface ProductTouchListener {
    fun onClickItem(product: Product)
}