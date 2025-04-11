package com.khaled.grocery.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.khaled.grocery.databinding.AddressItemBinding
import com.khaled.grocery.model.AddressData.Address

class AddressAdapter(
    private val onEditClickListener: (Address) -> Unit,
    private val onDeleteClickListener: (Address) -> Unit
) : ListAdapter<Address, AddressAdapter.AddressViewHolder>(DiffCallback()) {

    inner class AddressViewHolder(private val binding: AddressItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(address: Address) {
            binding.item = address
            binding.executePendingBindings()

            // Set the click listeners for Edit and Delete buttons
            binding.btnEdit.setOnClickListener {
                onEditClickListener(address)
            }

            binding.btnDelete.setOnClickListener {
                onDeleteClickListener(address)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val binding = AddressItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Address>() {
        override fun areItemsTheSame(old: Address, new: Address) = old.id == new.id
        override fun areContentsTheSame(old: Address, new: Address) = old == new
    }
}
