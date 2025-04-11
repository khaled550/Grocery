package com.khaled.grocery.ui.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.khaled.grocery.R
import com.khaled.grocery.databinding.ProductItemBinding
import com.khaled.grocery.model.Product

class ProductAdapter(private val listener: ProductTouchListener)
    : ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ProductViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ProductViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener.onClickItem(item)
        }
    }
    
    inner class ProductViewHolder(private val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product) {
            binding.item = item
            Glide.with(binding.productImg.context)
                .load(item.image)
                .placeholder(R.drawable.product_placeholder)
                .centerCrop()
                .transform(RoundedCorners(24))
                .into(binding.productImg)
            binding.executePendingBindings()
        }
    }
}

interface ProductTouchListener: BaseInteractListener {
    fun onClickItem(product: Product)
}

class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem // Checks all properties
    }
}

