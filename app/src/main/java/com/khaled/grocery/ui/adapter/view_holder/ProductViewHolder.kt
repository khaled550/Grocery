package com.khaled.grocery.ui.adapter.view_holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.khaled.grocery.R
import com.khaled.grocery.databinding.ProductItemBinding

class ProductViewHolder(v: View) : RecyclerView.ViewHolder(v) {
//    val productNameText: TextView = v.findViewById(R.id.productNameText)
//    val productImg: ImageView = v.findViewById(R.id.productImg)
    val binding = ProductItemBinding.bind(itemView)
}