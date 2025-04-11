package com.khaled.grocery.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.khaled.grocery.databinding.OrderItemBinding
import com.khaled.grocery.model.OrderSummaryData.OrderSummaryItem

class OrderAdapter : ListAdapter<OrderSummaryItem, OrderAdapter.OrderViewHolder>(DiffCallback()) {

    class OrderViewHolder(private val binding: OrderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(orderSummaryItem: OrderSummaryItem) {
            binding.apply {
                tvOrderId.text = "#${orderSummaryItem.id}"
                tvDate.text = orderSummaryItem.date
                tvTotal.text = "$${orderSummaryItem.total}"
                tvStatus.text = orderSummaryItem.status
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<OrderSummaryItem>() {
        override fun areItemsTheSame(old: OrderSummaryItem, new: OrderSummaryItem) = old.id == new.id
        override fun areContentsTheSame(old: OrderSummaryItem, new: OrderSummaryItem) = old == new
    }
}
