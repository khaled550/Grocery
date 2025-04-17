package com.khaled.grocery.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.khaled.grocery.databinding.OrderItemBinding
import com.khaled.grocery.model.OrderSummaryData.OrderSummaryItem

class OrderAdapter(
    private val listener: OrderTouchListener
) : ListAdapter<OrderSummaryItem, OrderAdapter.OrderViewHolder>(DiffCallback()) {

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
        val item = getItem(position)
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            listener.onClickItem(item.id)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<OrderSummaryItem>() {
        override fun areItemsTheSame(old: OrderSummaryItem, new: OrderSummaryItem) = old.id == new.id
        override fun areContentsTheSame(old: OrderSummaryItem, new: OrderSummaryItem) = old == new
    }
}

interface OrderTouchListener: BaseInteractListener {
    fun onClickItem(orderId: Int)
}

